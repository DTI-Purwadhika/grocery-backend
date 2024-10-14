package com.finpro.grocery.users.service.impl;
import com.finpro.grocery.auth.dto.SocialLoginRequestDTO;
import com.finpro.grocery.auth.service.impl.AuthRedisService;
import com.finpro.grocery.cloudinary.CloudinaryService;
import com.finpro.grocery.email.service.EmailService;
import com.finpro.grocery.referral.ReferralCodeGenerator;
import com.finpro.grocery.share.exception.ResourceNotFoundException;
import com.finpro.grocery.share.pagination.Pagination;
import com.finpro.grocery.users.dto.*;
import com.finpro.grocery.users.entity.User;
import com.finpro.grocery.users.repository.UserRepository;
import com.finpro.grocery.users.service.UserService;
import jakarta.mail.MessagingException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

@Service
@Log
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthRedisService authRedisService;
    private final CloudinaryService cloudinaryService;
    @Value("${baseurl.frontend}")
    private String baseUrl;

    public UserServiceImpl(UserRepository userRepository, EmailService emailService, PasswordEncoder passwordEncoder, AuthRedisService authRedisService, CloudinaryService cloudinaryService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.authRedisService = authRedisService;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public ProfileDataDTO getProfileData(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email not found"));

        ProfileDataDTO profileDataDTO = new ProfileDataDTO();
        profileDataDTO.setId(user.getId());
        profileDataDTO.setName(user.getName());
        profileDataDTO.setEmail(user.getEmail());
        profileDataDTO.setRole(user.getRole());
        profileDataDTO.setProfilePicture(cloudinaryService.generateUrl(user.getProfilePicture()));
        profileDataDTO.setReferralCode(user.getReferralCode());
        profileDataDTO.setIsVerified(user.getIsVerified());

        return profileDataDTO;
    }

    @Override
    public Pagination<CustomerResponseDTO> getAllUsers(String name, String roleKeyword, int page, int size, String sortBy, String sortDir) {
        User.UserRole userRole = null;

        if(!roleKeyword.isBlank()){
           userRole = User.UserRole.valueOf(roleKeyword);
        }

        Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? Sort.Order.desc(sortBy) : Sort.Order.asc(sortBy));
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<User> users = userRepository.getAll(name, userRole, pageable);

        Page<CustomerResponseDTO> usersDTO = users.map(CustomerResponseDTO::toDto);

        return new Pagination<>(
                usersDTO.getTotalPages(),
                usersDTO.getTotalElements(),
                usersDTO.isFirst(),
                usersDTO.isLast(),
                usersDTO.getContent()
        );
    }

    @Transactional
    @Override
    public ProfileDataDTO updateUser(String email, UpdateProfileDTO updateProfileDTO) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email not found"));
        ProfileDataDTO profileDataDTO = new ProfileDataDTO();

        if(updateProfileDTO.getProfilePicture() != null){

            if(user.getProfilePicture() != null){
                try{
                    cloudinaryService.deleteFile(user.getProfilePicture());
                }catch(IOException e){
                    throw new RuntimeException(e);
                }
            }

            try{
                user.setProfilePicture(cloudinaryService.uploadFile(updateProfileDTO.getProfilePicture()));
            }catch(IOException e){
                throw new RuntimeException(e);
            }
        }

        if(updateProfileDTO.getName() != null){
            user.setName(updateProfileDTO.getName());
        }

        if(updateProfileDTO.getEmail() != null){
            if(!updateProfileDTO.getEmail().equals(user.getEmail()) && userRepository.findByEmail(updateProfileDTO.getEmail()).isEmpty()){
                user.setIsVerified(false);

                String token = authRedisService.saveVerificationLink(updateProfileDTO.getEmail());

                sendVerificationEmail(updateProfileDTO.getEmail(), token);

                user.setEmail(updateProfileDTO.getEmail());
            }
            else if(!updateProfileDTO.getEmail().equals(user.getEmail()) && userRepository.findByEmail(updateProfileDTO.getEmail()).isPresent()) {
                profileDataDTO.setError("This email has been registered. Please update to a different email");
            }
        }

        if(updateProfileDTO.getPassword() != null){
            user.setPassword(updateProfileDTO.getPassword());
        }

        userRepository.save(user);

        profileDataDTO.setName(user.getName());
        profileDataDTO.setEmail(user.getEmail());
        profileDataDTO.setRole(user.getRole());
        profileDataDTO.setProfilePicture(cloudinaryService.generateUrl(user.getProfilePicture()));
        profileDataDTO.setReferralCode(user.getReferralCode());
        profileDataDTO.setIsVerified(user.getIsVerified());

        return profileDataDTO;
    }

    @Transactional
    @Override
    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email not found"));
        userRepository.delete(user);
    }

    @Override
    public Boolean isSocialLogin(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        return user.isPresent() && user.get().getIsVerified() && (user.get().getPassword() == null);
    }

    @Override
    public void saveUserSocialLogin(SocialLoginRequestDTO socialLoginRequestDTO) {
        User newUser = new User();
        newUser.setReferralCode(ReferralCodeGenerator.generateCode());
        newUser.setRole(socialLoginRequestDTO.getRole());
        newUser.setEmail(socialLoginRequestDTO.getEmail());
        newUser.setName(socialLoginRequestDTO.getName());
        newUser.setIsVerified(true);

        userRepository.save(newUser);
    }

    @Transactional
    @Override
    public User setPassword(SetPasswordDTO setPasswordDTO) {
        User user = userRepository.findById(Long.parseLong(setPasswordDTO.getId())).orElseThrow(() -> new ResourceNotFoundException("Email not found"));

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
        User user = userRepository.findById(Long.parseLong(checkVerificationLinkDTO.getId())).orElseThrow(() -> new ResourceNotFoundException("Email not found"));
        String token = authRedisService.getVerificationLink(user.getEmail());

        if ( !user.getIsVerified() && authRedisService.isVerificationLinkValid(user.getEmail()) && token.equals(checkVerificationLinkDTO.getToken()) ) {
            return "User not verified";
        } else if ( user.getIsVerified() ) {
            return "User verified";
        }
        else {
            return "Expired";
        }
}

    @Override
    public void newVerificationLink(String id) {
        User user = userRepository.findById(Long.parseLong(id)).orElseThrow(() -> new ResourceNotFoundException("Email not found"));
        String email = user.getEmail();

        if(!user.getIsVerified()){
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
        User user = userRepository.findById(Long.parseLong(checkResetPasswordLinkDTO.getId())).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        String email = user.getEmail();
        String token = authRedisService.getResetPasswordLink(email);

        return user.getIsVerified() && authRedisService.isResetPasswordLinkValid(email) && token.equals(checkResetPasswordLinkDTO.getToken());
    }

    @Override
    public void newResetPasswordLink(String id) {
        User user = userRepository.findById(Long.parseLong(id)).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        String email = user.getEmail();
        if(authRedisService.isResetPasswordLinkValid(email)){
            authRedisService.deleteResetPasswordLink(email);
        }

        String token = authRedisService.saveResetPasswordLink(email);

        sendResetPasswordEmail(email, token);
    }

    @Override
    public void sendVerificationEmail(String email, String token){
        User user = userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Email not found"));
        String userId = user.getId().toString();
        String subject = "Verify Your Account";
        String htmlMessage = "<!DOCTYPE html><html lang='en'> <head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Verify Your Email</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            background-color: #f4f4f4;\n" +
                "            color: #333333;\n" +
                "        }\n" +
                "        .container {\n" +
                "            width: 100%;\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            background-color: #ffffff;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 8px;\n" +
                "            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        .header {\n" +
                "            text-align: center;\n" +
                "            padding: 10px 0;\n" +
                "            background-color: #4CAF50;\n" +
                "            color: #ffffff;\n" +
                "            border-radius: 8px 8px 0 0;\n" +
                "        }\n" +
                "        .content {\n" +
                "            padding: 20px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        .button {\n" +
                "            display: inline-block;\n" +
                "            padding: 12px 24px;\n" +
                "            margin: 20px 0;\n" +
                "            background-color: #4CAF50;\n" +
                "            color: #ffffff;\n" +
                "            text-decoration: none;\n" +
                "            border-radius: 5px;\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            padding: 10px;\n" +
                "            text-align: center;\n" +
                "            font-size: 12px;\n" +
                "            color: #888888;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"header\">\n" +
                "            <h1>Verify Your Email</h1>\n" +
                "        </div>\n" +
                "        <div class=\"content\">\n" +
                "            <p>Hi,</p>\n" +
                "            <p>Thank you for registering! Please click the button below to verify your email address and complete your registration.</p>\n" +
                "            <a href=\"{{baseUrl}}/set-password?token={{token}}&userid={{userId}}\" class=\"button\">Verify Email</a>\n" +
                "            <p>If the button doesn't work, please copy and paste the following link into your browser:</p>\n" +
                "            <p><a href=\"{{baseUrl}}/set-password?token={{token}}&userid={{userId}}\">{{baseUrl}}/set-password?token={{token}}&userid={{userId}}</a></p>\n" +
                "        </div>\n" +
                "        <div class=\"footer\">\n" +
                "            <p>If you did not request this email, you can safely ignore it.</p>\n" +
                "            <p>&copy; 2024 Grocery APP. All rights reserved.</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body> </html>";

        htmlMessage = htmlMessage.replace("{{token}}", token);
        htmlMessage = htmlMessage.replace("{{userId}}", userId);
        htmlMessage = htmlMessage.replace("{{baseUrl}}", baseUrl);

        try{
            emailService.sendEmail(email, subject, htmlMessage);
        } catch (MessagingException e) {
            e.getLocalizedMessage();
        }
    }

    @Override
    public void sendResetPasswordEmail(String email, String token){
        User user = userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Email not found"));
        String userId = user.getId().toString();
        String subject = "Reset Account Password";
        String htmlMessage = "<!DOCTYPE html><html lang='en'> <head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Verify Your Email</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            background-color: #f4f4f4;\n" +
                "            color: #333333;\n" +
                "        }\n" +
                "        .container {\n" +
                "            width: 100%;\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            background-color: #ffffff;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 8px;\n" +
                "            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        .header {\n" +
                "            text-align: center;\n" +
                "            padding: 10px 0;\n" +
                "            background-color: #4CAF50;\n" +
                "            color: #ffffff;\n" +
                "            border-radius: 8px 8px 0 0;\n" +
                "        }\n" +
                "        .content {\n" +
                "            padding: 20px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        .button {\n" +
                "            display: inline-block;\n" +
                "            padding: 12px 24px;\n" +
                "            margin: 20px 0;\n" +
                "            background-color: #4CAF50;\n" +
                "            color: #ffffff;\n" +
                "            text-decoration: none;\n" +
                "            border-radius: 5px;\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            padding: 10px;\n" +
                "            text-align: center;\n" +
                "            font-size: 12px;\n" +
                "            color: #888888;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"header\">\n" +
                "            <h1>Verify Your Email</h1>\n" +
                "        </div>\n" +
                "        <div class=\"content\">\n" +
                "            <p>Hi,</p>\n" +
                "            <p>Please click the button below to reset your password.</p>\n" +
                "            <a href=\"{{baseUrl}}/reset-password?token={{token}}&userid={{userId}}\" class=\"button\">Reset Password</a>\n" +
                "            <p>If the button doesn't work, please copy and paste the following link into your browser:</p>\n" +
                "            <p><a href=\"{{baseUrl}}/reset-password?token={{token}}&userid={{userId}}\">{{baseUrl}}/reset-password?token={{token}}&userid={{userId}}</a></p>\n" +
                "        </div>\n" +
                "        <div class=\"footer\">\n" +
                "            <p>If you did not request this email, you can safely ignore it.</p>\n" +
                "            <p>&copy; 2024 Grocery APP. All rights reserved.</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body> </html>";

        htmlMessage = htmlMessage.replace("{{token}}", token);
        htmlMessage = htmlMessage.replace("{{userId}}", userId);
        htmlMessage = htmlMessage.replace("{{baseUrl}}", baseUrl);

        try{
            emailService.sendEmail(email, subject, htmlMessage);
        } catch (MessagingException e) {
            e.getLocalizedMessage();
        }
    }
}
