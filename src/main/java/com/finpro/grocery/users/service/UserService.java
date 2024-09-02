package com.finpro.grocery.users.service;

import com.finpro.grocery.users.dto.RegisterUserDTO;
import com.finpro.grocery.users.dto.SetPasswordDTO;
import com.finpro.grocery.users.entity.User;
import jakarta.mail.MessagingException;

public interface UserService {

    public User register(RegisterUserDTO registerUserDTO);
    public void sendVerificationEmail(User user, String token);
    public User verifyAccount(SetPasswordDTO setPasswordDTO, String email);
}
