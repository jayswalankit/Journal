package com.ankit.journalApp.Config;

import com.ankit.journalApp.Service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    /// Constructor dependency
    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
  /// /- SecurityFilterChain ek bean banaya hai jo Spring Security ke filter rules define karta hai.
  /// - HttpSecurity parameter ke through tum request authorization aur authentication rules set kar rahe ho.
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth            /// authorization config block hai yahi hum define karte hai ki kaunsa url endpoints kis tarah user ke liye accessible hai
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/journal/**").authenticated()
                        .requestMatchers("/user/**").authenticated()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                      .anyRequest().permitAll()
                )
                .userDetailsService(userDetailsService)
                .httpBasic(Customizer.withDefaults())///  isme jab hum request bhejte hai to browser ya postman ek popup ya header ke through username/password bhejta hai ....
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
