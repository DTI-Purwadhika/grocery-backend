package com.finpro.grocery.users.service;

import com.finpro.grocery.users.dto.*;
import com.finpro.grocery.users.entity.User;

import java.util.Optional;

public interface UserService {
    public Optional<User> getUserByEmail(String email);
    public ProfileDataDTO getProfileData(String email);
    public User setPassword(SetPasswordDTO setPasswordDTO);
    public User register(RegisterUserDTO registerUserDTO);
    public String checkVerificationLink(CheckVerificationLinkDTO checkVerificationLinkDTO);
    public void newVerificationLink(String email);
    public String resetPassword(String email);
    public Boolean checkResetPasswordLink(CheckResetPasswordLinkDTO checkResetPasswordLinkDTO);
    public void newResetPasswordLink(String email);
    public void sendVerificationEmail(String email, String token);
    public void sendResetPasswordEmail(String email, String token);
}
