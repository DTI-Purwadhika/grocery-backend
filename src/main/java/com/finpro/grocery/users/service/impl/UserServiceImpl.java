package com.finpro.grocery.users.service.impl;
import com.finpro.grocery.auth.dto.SocialLoginRequestDTO;
import com.finpro.grocery.auth.service.impl.AuthRedisService;
import com.finpro.grocery.email.service.EmailService;
import com.finpro.grocery.referral.ReferralCodeGenerator;
import com.finpro.grocery.share.exception.ResourceNotFoundException;
import com.finpro.grocery.users.dto.*;
import com.finpro.grocery.users.entity.User;
import com.finpro.grocery.users.repository.UserRepository;
import com.finpro.grocery.users.service.UserService;
import jakarta.mail.MessagingException;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Service
@Log
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthRedisService authRedisService;

    public UserServiceImpl(UserRepository userRepository, EmailService emailService, PasswordEncoder passwordEncoder, AuthRedisService authRedisService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.authRedisService = authRedisService;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public ProfileDataDTO getProfileData(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email not found"));

        return ProfileDataDTO.toDto(user);
    }

    @Override
    public Boolean isSocialLogin(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        return user.isPresent() && user.get().getIsVerified() && (user.get().getPassword() == null);
    }

    @Override
    public void saveUserSocialLogin(SocialLoginRequestDTO socialLoginRequestDTO) {
        User newUser = new User();
        newUser.setRole(socialLoginRequestDTO.getRole());
        newUser.setEmail(socialLoginRequestDTO.getEmail());
        newUser.setName(socialLoginRequestDTO.getName());
        newUser.setProfilePicture(socialLoginRequestDTO.getProfilePicture());
        newUser.setIsVerified(true);

        userRepository.save(newUser);
    }

    @Transactional
    @Override
    public User setPassword(SetPasswordDTO setPasswordDTO) {
        User user = userRepository.findByEmail(setPasswordDTO.getEmail()).orElseThrow(() -> new ResourceNotFoundException("Email not found"));

        if (!user.getIsVerified()) {
            user.setIsVerified(true);
        }

        if(authRedisService.isResetPasswordLinkValid(user.getEmail())){
            authRedisService.deleteResetPasswordLink(user.getEmail());
        }

        user.setUpdatedAt(Instant.now());
        user.setPassword(passwordEncoder.encode(setPasswordDTO.getPassword()));
        userRepository.save(user);

        return user;
    }

    @Transactional
    @Override
    public String register(RegisterUserDTO registerUserDTO) {
        if (userRepository.findByEmail(registerUserDTO.getEmail()).isPresent()) {
            if(isSocialLogin(registerUserDTO.getEmail())){
                return "Email is already registered with social account";
            }

            return "An account with this email has already been registered";
        }

        User registeredUser = registerUserDTO.toEntity();

        if (registeredUser.getRole() == User.UserRole.CUSTOMER) {
            String code = ReferralCodeGenerator.generateCode();
            registeredUser.setReferralCode(code);
        }

        userRepository.save(registeredUser);

        String token = authRedisService.saveVerificationLink(registeredUser.getEmail());

        sendVerificationEmail(registeredUser.getEmail(), token);

        return "Success";
    }

    @Override
    public String checkVerificationLink(CheckVerificationLinkDTO checkVerificationLinkDTO) {
        User user = userRepository.findByEmail(checkVerificationLinkDTO.getEmail()).orElseThrow(() -> new ResourceNotFoundException("Email not found"));
        String token = authRedisService.getVerificationLink(checkVerificationLinkDTO.getEmail());

        if ( !user.getIsVerified() && authRedisService.isVerificationLinkValid(checkVerificationLinkDTO.getEmail()) && token.equals(checkVerificationLinkDTO.getToken()) ) {
            return "User not verified";
        } else if ( user.getIsVerified() ) {
            return "User verified";
        }
        else {
            return "Expired";
        }
}

    @Override
    public void newVerificationLink(String email) {
        Optional<User> user = userRepository.findByEmail((email));

        if(user.isPresent() && !user.get().getIsVerified()){
            if(authRedisService.isVerificationLinkValid(email)){
                authRedisService.deleteVerificationLink(email);
            }

            String token = authRedisService.saveVerificationLink(email);

            sendVerificationEmail(email, token);
        }
    }

    @Override
    public String resetPassword(String email) {
        Optional<User> user = userRepository.findByEmail((email));

        if(user.isEmpty()){
            return "User not registered";
        }

        if(!user.get().getIsVerified()){
            return "User not verified";
        }

        if(authRedisService.isResetPasswordLinkValid(email)){
            authRedisService.deleteResetPasswordLink(email);
        }

        String token = authRedisService.saveResetPasswordLink(email);

        sendResetPasswordEmail(user.get().getEmail(), token);

        return "Successful";
    }

    @Override
    public Boolean checkResetPasswordLink(CheckResetPasswordLinkDTO checkResetPasswordLinkDTO) {
        User user = userRepository.findByEmail(checkResetPasswordLinkDTO.getEmail()).orElseThrow(()-> new ResourceNotFoundException("Email not found"));
        String token = authRedisService.getResetPasswordLink(checkResetPasswordLinkDTO.getEmail());

        return user.getIsVerified() && authRedisService.isResetPasswordLinkValid(checkResetPasswordLinkDTO.getEmail()) && token.equals(checkResetPasswordLinkDTO.getToken());
    }

    @Override
    public void newResetPasswordLink(String email) {
        if(authRedisService.isResetPasswordLinkValid(email)){
            authRedisService.deleteResetPasswordLink(email);
        }

        String token = authRedisService.saveResetPasswordLink(email);

        sendResetPasswordEmail(email, token);
    }

    @Override
    public void sendVerificationEmail(String email, String token){
        String subject = "Verify Your Account";
        String htmlMessage = "<html>" +
                "<body style='margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f4f4f4;'>" +
                "<div style='max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px;'>" +

                "<div style='text-align: center; background-color: #FABC3F; padding: 20px;'>" +
                "</div>" +

                "<div style='text-align: center; padding: 40px 20px;'>" +
                "<h1 style='color: #E85C0D;'>Thanks for Signing Up!</h1>" +
                "<h2 style='color: #E85C0D;'>Verify Your Email</h2>" +
                "<p style='color: #333;'>Hi,<br>Your registration is almost complete. Please click on the button below to verify your email address</p>" +
                "<a href='http://localhost:3000/set-password?token=" + token +"&email=" + email + "' style='text-decoration: none;'>" +
                "<button style='background-color: #C7253E; color: white; padding: 15px 30px; border: none; border-radius: 5px; font-size: 16px; cursor: pointer;'>VERIFY YOUR EMAIL</button>" +
                "</a>" +
                "</div>" +

                "<div style='background-color: #e0e0e0; padding: 20px; text-align: center;'>" +
                "<p style='color: #333;'>Get in touch:<br>+62 123 456 789<br>xyz@gmail.com</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";

        try{
            emailService.sendEmail(email, subject, htmlMessage);
        } catch (MessagingException e) {
            e.getLocalizedMessage();
        }
    }

    @Override
    public void sendResetPasswordEmail(String email, String token){
        String subject = "Reset Account Password";
        String htmlMessage = "<html>" +
                "<body style='margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f4f4f4;'>" +
                "<div style='max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px;'>" +

                "<div style='text-align: center; background-color: #FABC3F; padding: 20px;'>" +
                "</div>" +

                "<div style='text-align: center; padding: 40px 20px;'>" +
                "<h1 style='color: #E85C0D;'>Reset your Password</h1>" +
                "<p style='color: #333;'>Hi,<br>Please click the button below to reset your password.</p>" +
                "<a href='http://localhost:3000/reset-password?token=" + token +"&email=" + email + "' style='text-decoration: none;'>" +
                "<button style='background-color: #C7253E; color: white; padding: 15px 30px; border: none; border-radius: 5px; font-size: 16px; cursor: pointer;'>RESET PASSWORD</button>" +
                "</a>" +
                "</div>" +

                "<div style='background-color: #e0e0e0; padding: 20px; text-align: center;'>" +
                "<p style='color: #333;'>Get in touch:<br>+62 123 456 789<br>xyz@gmail.com</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";

        try{
            emailService.sendEmail(email, subject, htmlMessage);
        } catch (MessagingException e) {
            e.getLocalizedMessage();
        }
    }
}
