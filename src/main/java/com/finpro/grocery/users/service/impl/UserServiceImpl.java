package com.finpro.grocery.users.service.impl;

import com.finpro.grocery.auth.entity.VerificationToken;
import com.finpro.grocery.auth.repository.VerificationTokenRepository;
import com.finpro.grocery.auth.service.VerificationService;
import com.finpro.grocery.email.service.EmailService;
import com.finpro.grocery.referral.ReferralCodeGenerator;
import com.finpro.grocery.share.exception.ResourceAlreadyExistsException;
import com.finpro.grocery.share.exception.ResourceNotFoundException;
import com.finpro.grocery.users.dto.RegisterUserDTO;
import com.finpro.grocery.users.dto.SetPasswordDTO;
import com.finpro.grocery.users.entity.User;
import com.finpro.grocery.users.repository.UserRepository;
import com.finpro.grocery.users.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final VerificationService verificationService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, EmailService emailService, VerificationService verificationService, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.verificationService = verificationService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public User register(RegisterUserDTO registerUserDTO) {
        if(userRepository.findByEmail(registerUserDTO.getEmail()).isPresent()){
            throw new ResourceAlreadyExistsException("Email has already been registered");
        }

        User registeredUser = registerUserDTO.toEntity();

        if(registeredUser.getRole() == User.UserRole.CUSTOMER){
            String code = ReferralCodeGenerator.generateCode();
            registeredUser.setReferralCode(code);
        }

        userRepository.save(registeredUser);

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(registeredUser);
        verificationToken.setExpiryDate(LocalDateTime.now().plusHours(1));
        verificationService.saveToken(verificationToken);

        sendVerificationEmail(registeredUser, verificationToken.getToken());

        return registeredUser;
    }

    @Override
    public void sendVerificationEmail(User user, String token){
        String subject = "Verify Your Account";
        String htmlMessage = "<html>" +
                "<body style='margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f4f4f4;'>" +
                "<div style='max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px;'>" +

                "<div style='text-align: center; background-color: #FABC3F; padding: 20px;'>" +
//                "<img src='https://res.cloudinary.com/dv9bbdl6i/image/upload/v1724211850/HiiMart/hiimartV1_rgwy5e.png' alt='Your Site' style='width: 50px;'>"
                 "</div>" +

                "<div style='text-align: center; padding: 40px 20px;'>" +
                "<h1 style='color: #E85C0D;'>Thanks for Signing Up!</h1>" +
                "<h2 style='color: #E85C0D;'>Verify Your Email</h2>" +
                "<p style='color: #333;'>Hi,<br>You're almost ready to get started. Please click on the button below to verify your email address</p>" +
                "<a href='http://localhost:3000/api/v1/users/verify?token=" + token +"&email=" + user.getEmail() + "' style='text-decoration: none;'>" +
                "<button style='background-color: #C7253E; color: white; padding: 15px 30px; border: none; border-radius: 5px; font-size: 16px; cursor: pointer;'>VERIFY YOUR EMAIL</button>" +
                "</a>" +
                "</div>" +

                "<div style='background-color: #e0e0e0; padding: 20px; text-align: center;'>" +
                "<p style='color: #333;'>Get in touch:<br>+62 815 8608 1551<br>HiiMart@gmail.com</p>" +
                "<p style='color: #999;'>Copyrights © Company All Rights Reserved</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";

       try{
           emailService.sendEmail(user.getEmail(), subject, htmlMessage);
       } catch (MessagingException e) {
            e.getLocalizedMessage();
       }
    }

    @Override
    public User verifyAccount(SetPasswordDTO setPasswordDTO, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Email not found"));

        if(!user.getIsVerified()){
            user.setIsVerified(true);
        }

        user.setUpdatedAt(Instant.now());
        user.setPassword(passwordEncoder.encode(setPasswordDTO.getPassword()));
        userRepository.save(user);

        return user;
    }
}
