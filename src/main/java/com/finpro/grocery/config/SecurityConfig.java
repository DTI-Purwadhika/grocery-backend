package com.finpro.grocery.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.http.Cookie;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Log
public class SecurityConfig {
    private final RsaKeyConfigProperties rsaKeyConfigProperties;
    private final UserDetailsService userDetailsService;
    private final CorsConfigSourceImpl corsConfigSource;

    public SecurityConfig(RsaKeyConfigProperties rsaKeyConfigProperties, UserDetailsService userDetailsService, CorsConfigSourceImpl corsConfigSource){
        this.rsaKeyConfigProperties = rsaKeyConfigProperties;
        this.userDetailsService = userDetailsService;
        this.corsConfigSource = corsConfigSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector handlerMappingIntrospector) throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigSource))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/error/**").permitAll();
                    auth.requestMatchers("/api/auth/**").permitAll();
                    auth.requestMatchers("/api/v1/users/register").permitAll();
                    auth.requestMatchers("/api/v1/users/set-password").permitAll();
                    auth.requestMatchers("/api/v1/users/check-verification-link").permitAll();
                    auth.requestMatchers("/api/v1/users/new-verification-link").permitAll();
                    auth.requestMatchers("/api/v1/users/reset-password").permitAll();
                    auth.requestMatchers("/api/v1/users/check-reset-password-link").permitAll();
                    auth.requestMatchers("/api/v1/users/new-reset-password-link").permitAll();
                    auth.requestMatchers("/api/v1/products/**").permitAll();
                    auth.requestMatchers("/api/v1/categories/**").permitAll();

                    auth.requestMatchers("/api/addresses/**").hasAuthority("SCOPE_CUSTOMER");
                    auth.requestMatchers("/api/v1/cart/**").hasAuthority("SCOPE_CUSTOMER");

                    auth.requestMatchers("/api/v1/users").hasAuthority("SCOPE_SUPER");
                    auth.requestMatchers("/api/v1/admins/**").hasAuthority("SCOPE_SUPER");
                    auth.requestMatchers("/api/v1/stores/**").hasAuthority("SCOPE_SUPER");
                    auth.requestMatchers("/api/v1/sale-history/**").hasAuthority("SCOPE_SUPER");
                    auth.requestMatchers("/api/v1/stock-history/**").hasAuthority("SCOPE_SUPER");
                    auth.requestMatchers("/api/v1/inventory/**").hasAuthority("SCOPE_SUPER");
                    auth.requestMatchers("/api/v1/promotions/**").hasAuthority("SCOPE_SUPER");
                    auth.requestMatchers("/api/v1/checkouts/**").hasAuthority("SCOPE_SUPER");

                    auth.requestMatchers("/api/v1/inventory/**").hasAuthority("SCOPE_ADMIN");
                    auth.requestMatchers("/api/v1/promotions/**").hasAuthority("SCOPE_ADMIN");
                    auth.requestMatchers("/api/v1/users").hasAuthority("SCOPE_ADMIN");
                    auth.requestMatchers("/api/v1/admins/**").hasAuthority("SCOPE_ADMIN");
                    auth.requestMatchers("/api/v1/stores/**").hasAuthority("SCOPE_ADMIN");

                    auth.anyRequest().authenticated();
                })
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer((oauth2) -> {
                    oauth2.jwt((jwt) -> jwt.decoder(jwtDecoder()));
                    oauth2.bearerTokenResolver((request) -> {
                        Cookie[] cookies = request.getCookies();
                        var authHeader = request.getHeader("Authorization");
                        if (cookies != null){
                            for (Cookie cookie : cookies){
                                if ("Sid".equals(cookie.getName())){
                                    return cookie.getValue();
                                }
                            }
                        }else if (authHeader!= null && !authHeader.isEmpty()) {
                            return authHeader.replace("Bearer ", "");
                        }
                        return null;
                    });
                })
                .userDetailsService(userDetailsService)
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(rsaKeyConfigProperties.publicKey()).build();
    }

    @Bean
    public JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey.Builder(rsaKeyConfigProperties.publicKey()).privateKey(rsaKeyConfigProperties.privateKey()).build();

        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }
}
