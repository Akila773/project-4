package com.example.vehical_rental.controller;

import com.example.vehical_rental.model.UserDtls;
import com.example.vehical_rental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/Cuser")
    public String afterLogin(Authentication authentication, Model model) {
        String email = authentication.getName(); // logged-in user’s email
        UserDtls user = userRepository.findByEmail(email);

        model.addAttribute("user", user);
        return "User-dashboard";
    }





}
