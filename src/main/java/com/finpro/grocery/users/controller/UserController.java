package com.finpro.grocery.users.controller;

import com.finpro.grocery.share.response.ApiResponse;
import com.finpro.grocery.users.dto.CheckResetPasswordLinkDTO;
import com.finpro.grocery.users.dto.CheckVerificationLinkDTO;
import com.finpro.grocery.users.dto.RegisterUserDTO;
import com.finpro.grocery.users.dto.SetPasswordDTO;
import com.finpro.grocery.users.service.UserService;
import lombok.extern.java.Log;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Validated
@Log
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/set-password")
    public ApiResponse<?> setPassword(@RequestBody SetPasswordDTO setPasswordDTO){
        return new ApiResponse<>("OK", "200", userService.setPassword(setPasswordDTO));
    }

    @PostMapping("/register")
    public ApiResponse<?> register(@RequestBody RegisterUserDTO registerUserDTO){
        return new ApiResponse<>("OK", "200", userService.register(registerUserDTO));
    }

    @PostMapping("/check-verification-link")
    public ApiResponse<?> checkVerificationLink(@RequestBody CheckVerificationLinkDTO checkVerificationLinkDTO){
        return new ApiResponse<>("OK", "200", userService.checkVerificationLink(checkVerificationLinkDTO));
    }

    @PostMapping("/new-verification-link")
    public ApiResponse<?> newVerificationLink(@RequestParam String email){
        userService.newVerificationLink(email);
        return new ApiResponse<>("OK", "200", "Verification link sent successfully");
    }

    @PostMapping("/reset-password")
    public ApiResponse<?> resetPassword(@RequestParam String email){
        return new ApiResponse<>("OK", "200", userService.resetPassword(email));
    }

    @PostMapping("/check-reset-password-link")
    public ApiResponse<?> checkResetPasswordLink(@RequestBody CheckResetPasswordLinkDTO checkResetPasswordLinkDTO){
        return new ApiResponse<>("OK","200", userService.checkResetPasswordLink(checkResetPasswordLinkDTO));
    }

    @PostMapping("/new-reset-password-link")
    public ApiResponse<?> newResetPasswordLink(@RequestParam String email){
        userService.newResetPasswordLink(email);
        return new ApiResponse<>("OK", "200", "Reset password link sent successfully");
    }
}
