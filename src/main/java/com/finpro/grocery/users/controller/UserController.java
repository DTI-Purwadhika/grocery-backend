package com.finpro.grocery.users.controller;

import com.finpro.grocery.auth.entity.VerificationToken;
import com.finpro.grocery.auth.service.VerificationService;
import com.finpro.grocery.share.response.ApiResponse;
import com.finpro.grocery.users.dto.RegisterUserDTO;
import com.finpro.grocery.users.dto.SetPasswordDTO;
import com.finpro.grocery.users.service.UserService;
import lombok.extern.java.Log;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@Validated
@Log
public class UserController {
    private final UserService userService;
    private final VerificationService verificationService;

    public UserController(UserService userService, VerificationService verificationService){
        this.userService = userService;
        this.verificationService = verificationService;
    }

    @PostMapping("/register")
    public ApiResponse<?> register(@RequestBody RegisterUserDTO registerUserDTO){
        return new ApiResponse<>("OK", "200", userService.register(registerUserDTO));
    }

    @PostMapping("/verify")
    public ApiResponse<?> verifyEmail(@RequestBody SetPasswordDTO setPasswordDTO, @RequestParam String email, @RequestParam String token){
        Optional<VerificationToken> verificationToken = Optional.ofNullable(verificationService.getTokenByValue(token));

        if(verificationToken.isEmpty()){
            return new ApiResponse<>("Bad Request", "400", "Invalid token");
        }

        VerificationToken userToken = verificationToken.get();

        if(userToken.getExpiryDate().isBefore(LocalDateTime.now())){
            return new ApiResponse<>("Bad Request", "400", "Token expired");
        }

        return new ApiResponse<>("OK", "200", userService.verifyAccount(setPasswordDTO, email));
    }
}
