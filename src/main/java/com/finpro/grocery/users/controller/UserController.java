package com.finpro.grocery.users.controller;

import com.finpro.grocery.auth.helper.Claims;
import com.finpro.grocery.share.pagination.Pagination;
import com.finpro.grocery.share.response.ApiResponse;
import com.finpro.grocery.users.dto.*;
import com.finpro.grocery.users.service.UserService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@Validated
@Log
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/userid")
    public ApiResponse<String> getUserId(@RequestParam String email){
        Long  userId = userService.getUserByEmail(email).get().getId();
        return new ApiResponse<>("OK", "200", String.valueOf(userId));
    }

    @GetMapping("")
    public ApiResponse<Pagination<CustomerResponseDTO>> getAllUsers(@RequestParam(defaultValue = "") String keyword,
                                               @RequestParam(defaultValue = "CUSTOMER") String roleKeyword,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size,
                                               @RequestParam(defaultValue = "id") String sortBy,
                                               @RequestParam(defaultValue = "asc") String sortDir){
        return new ApiResponse<>("OK", "200", userService.getAllUsers(keyword, roleKeyword, page, size, sortBy, sortDir));
    }

    @GetMapping("/profile")
    public ApiResponse<?> getProfileData(){
        var claims = Claims.getClaimsFromJwt();
        String currentUserEmail = (String) claims.get("sub");

        log.info("Claims are: " + claims.toString());
        log.info("Profile data requested for user: " + currentUserEmail);
        return new ApiResponse<>("OK", "200", userService.getProfileData(currentUserEmail));
    }

    @PutMapping("/update")
    public ApiResponse<?> updateProfile(@ModelAttribute UpdateProfileDTO updateProfileDTO){
        var claims = Claims.getClaimsFromJwt();
        String currentUserEmail = (String) claims.get("sub");

        return new ApiResponse<>("OK", "200", userService.updateUser(currentUserEmail, updateProfileDTO));
    }

    @DeleteMapping("/delete")
    public ApiResponse<?> deleteProfile(){
        var claims = Claims.getClaimsFromJwt();
        String currentUserEmail = (String) claims.get("sub");
        userService.deleteUser(currentUserEmail);
        return new ApiResponse<>("OK", "200", "User profile deleted successfully");
    }

    @PostMapping("/set-password")
    public ApiResponse<?> setPassword(@RequestBody SetPasswordDTO setPasswordDTO){
        return new ApiResponse<>("OK", "200", userService.setPassword(setPasswordDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserDTO registerUserDTO){
        String result = userService.register(registerUserDTO);

        return switch (result) {
            case "Email is already registered with social account" ->
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Email already registered with social account"));
            case "An account with this email has already been registered" ->
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Account with this email already registered"));
            case "A super admin account has been registered" ->
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "A super admin account has been registered"));
            default -> ResponseEntity.status(HttpStatus.OK).body(Map.of("success", "Account registered successfully"));
        };
    }

    @PostMapping("/check-verification-link")
    public ApiResponse<?> checkVerificationLink(@RequestBody CheckVerificationLinkDTO checkVerificationLinkDTO){
        return new ApiResponse<>("OK", "200", userService.checkVerificationLink(checkVerificationLinkDTO));
    }

    @PostMapping("/new-verification-link")
    public ApiResponse<?> newVerificationLink(@RequestParam String id){
        userService.newVerificationLink(id);
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
    public ApiResponse<?> newResetPasswordLink(@RequestParam String id){
        userService.newResetPasswordLink(id);
        return new ApiResponse<>("OK", "200", "Reset password link sent successfully");
    }
}
