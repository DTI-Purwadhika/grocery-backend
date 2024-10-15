package com.finpro.grocery.users.service;

import com.finpro.grocery.auth.dto.SocialLoginRequestDTO;
import com.finpro.grocery.share.pagination.Pagination;
import com.finpro.grocery.users.dto.*;
import com.finpro.grocery.users.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserService {
    public void saveUser(User user);
    public Optional<User> getUserByEmail(String email);
    public ProfileDataDTO getProfileData(String email);
    public Pagination<CustomerResponseDTO> getAllUsers(String name, String roleKeyword, int page, int size, String sortBy, String sortDir);
    public ProfileDataDTO updateUser(String email, UpdateProfileDTO updateProfileDTO);
    public void deleteUser(String email);
    public Boolean isSocialLogin(String email);
    public void saveUserSocialLogin(SocialLoginRequestDTO socialLoginRequestDTO);
    public User setPassword(SetPasswordDTO setPasswordDTO);
    public String register(RegisterUserDTO registerUserDTO);
    public String checkVerificationLink(CheckVerificationLinkDTO checkVerificationLinkDTO);
    public void newVerificationLink(String id);
    public String resetPassword(String email);
    public Boolean checkResetPasswordLink(CheckResetPasswordLinkDTO checkResetPasswordLinkDTO);
    public void newResetPasswordLink(String id);
    public void sendVerificationEmail(String email, String token);
    public void sendResetPasswordEmail(String email, String token);
}
