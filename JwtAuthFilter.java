package com.example.demo.Security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.Service.JwtService;
import com.example.demo.Service.UserDetailServiceImpl;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Component
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor

public  class JwtAuthFilter extends OncePerRequestFilter{
    
	private final UserDetailServiceImpl userDetailService;
    private final JwtService jwtService;
    public JwtAuthFilter(UserDetailServiceImpl userDetailService, JwtService jwtService) {
		super();
		this.userDetailService = userDetailService;
		this.jwtService = jwtService;
	}
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = GettokenFromRequest(request);
        if (StringUtils.hasText(token) && jwtService.ValidateToken(token)) {
            String userName = jwtService.getUserNameFromJwt(token);
            UserDetails userDetails = userDetailService.loadUserByUsername(userName);
            UsernamePasswordAuthenticationToken authenticationToken = new  UsernamePasswordAuthenticationToken(
                    userDetails ,
                    null,
                    userDetails.getAuthorities()
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);


    }
    private String GettokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}