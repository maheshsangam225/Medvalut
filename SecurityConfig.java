package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.Security.JwtAuthFilter;
import com.example.demo.Service.JwtService;
import com.example.demo.Service.UserDetailServiceImpl;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@NoArgsConstructor
@RequiredArgsConstructor

public  class SecurityConfig {
    	private final UserDetailServiceImpl userDetailService;
    private final JwtService jwtService;
    public SecurityConfig(UserDetailServiceImpl userDetailService, JwtService jwtService) {
		super();
		this.userDetailService = userDetailService;
		this.jwtService = jwtService;
	}

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration aunthenticationConfiguration)  throws Exception {
        return aunthenticationConfiguration.getAuthenticationManager();


    }
    @Bean
    JwtAuthFilter jwtAuthFilter(){
        return  new JwtAuthFilter(userDetailService, jwtService);

    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {
        return  httpSecurity
                .csrf(csrf->csrf.disable())
                .sessionManagement(customizer->customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter(),UsernamePasswordAuthenticationFilter.class)
                .build();

    }
}
