package com.finpro.grocery.auth.controller;

import com.finpro.grocery.auth.dto.LoginRequestDTO;
import com.finpro.grocery.auth.dto.LoginResponseDTO;
import com.finpro.grocery.auth.entity.UserAuth;
import com.finpro.grocery.auth.service.AuthService;
import com.finpro.grocery.share.response.ApiResponse;
import com.finpro.grocery.users.entity.User;
import com.finpro.grocery.users.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@Validated
@Log
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    public AuthController(AuthService authService, AuthenticationManager authenticationManager, UserService userService){
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO userLogin){
        log.info("User login request received for user: " + userLogin.getEmail());

        Optional<User> user = userService.getUserByEmail(userLogin.getEmail());

        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User with this email not found"));
        }

        if(!user.get().getIsVerified()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Email has not been verified"));
        }


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword()
                ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserAuth userDetails = (UserAuth) authentication.getPrincipal();
        log.info("Token requested for user " + userDetails.getUsername() + "with roles" + userDetails.getAuthorities());
        String token = authService.generateToken(authentication);

        LoginResponseDTO response = new LoginResponseDTO();
        response.setEmail(userDetails.getUsername());
        response.setToken(token);

        Cookie cookie = new Cookie("sid", token);
        cookie.setPath("/");
        cookie.setMaxAge(5 * 60 * 60);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-cookie", cookie.getName() + "=" + cookie.getValue() + "; Path=/; HttpOnly");

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);
    }

    @PostMapping("/logout")
    public ApiResponse<?> logout(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies){
                if ("sid".equals(cookie.getName())){
                    cookie.setMaxAge(0);
                    cookie.setValue(null);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
            authService.logout();
        }

        return new ApiResponse<>("OK", "200", "Logout successful");
    }

}