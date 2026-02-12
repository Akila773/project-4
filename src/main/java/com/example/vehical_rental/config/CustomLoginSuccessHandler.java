package com.example.vehical_rental.config;

import com.example.vehical_rental.model.UserDtls;
import com.example.vehical_rental.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String email = authentication.getName(); // logged-in username/email
        UserDtls user = userRepository.findByEmail(email);

        if (user != null) {
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);
        }

        // redirect based on role
        String redirectUrl = "/Cuser";
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            redirectUrl = "/admin";
        }else if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
            redirectUrl = "/Cuser";
        }
        response.sendRedirect( redirectUrl);
    }
}

