package com.example.vehical_rental.controller;

import com.example.vehical_rental.model.UserDtls;
import com.example.vehical_rental.repository.UserRepository;
import org.apache.tomcat.util.modeler.BaseAttributeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin")
    public String admin(Authentication authentication, Model model){
        String email = authentication.getName(); // logged-in user’s email
        UserDtls admin = userRepository.findByEmail(email);

        model.addAttribute("admin", admin);


        return "addmin-dashboard";
    }
}