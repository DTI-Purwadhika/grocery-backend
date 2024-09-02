package com.finpro.grocery.auth.controller;

import com.finpro.grocery.auth.dto.LoginRequestDTO;
import com.finpro.grocery.auth.dto.LoginResponseDTO;
import com.finpro.grocery.auth.entity.UserAuth;
import com.finpro.grocery.auth.service.AuthService;
import com.finpro.grocery.share.response.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Validated
@Log
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    public AuthController(AuthService authService, AuthenticationManager authenticationManager){
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody LoginRequestDTO userLogin){
        log.info("User login request received for user: " + userLogin.getEmail());

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
        cookie.setMaxAge(3600);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-cookie", cookie.getName() + "=" + cookie.getValue() + "; Path=/; HttpOnly");

        return new ApiResponse<>("OK", "200", response);
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
        }

        return new ApiResponse<>("OK", "200", "Logout successful");
    }

}