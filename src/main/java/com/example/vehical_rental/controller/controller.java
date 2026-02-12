package com.example.vehical_rental.controller;

import aj.org.objectweb.asm.ConstantDynamic;
import com.example.vehical_rental.model.UserDtls;
import com.example.vehical_rental.repository.UserRepository;
import com.example.vehical_rental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class controller {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/signin")
    public String login() {
        return "signin";
    }


    @GetMapping("/register")
    public String register() {
        return "register";
    }





    @PostMapping("/createUser")
    public String createUser(@ModelAttribute UserDtls user, RedirectAttributes redirectAttributes) {

        // Manual validation
        if (user.getFullName() == null || user.getFullName().trim().isEmpty() ||
                user.getEmail() == null || user.getEmail().trim().isEmpty() ||
                user.getAddress() == null || user.getAddress().trim().isEmpty() ||
                user.getPhone() == null || user.getPhone().trim().isEmpty() ||
                user.getPassword() == null || user.getPassword().trim().isEmpty()) {

            redirectAttributes.addFlashAttribute("msg", " All fields are required!");
            return "redirect:/register";
        }

        // Check email duplication
        boolean exists = userService.checkEmail(user.getEmail());
        if (exists) {
            redirectAttributes.addFlashAttribute("msg", " Email already exists!");
            return "redirect:/register";
        }
        // Save user
        UserDtls userDtls = userService.createUser(user);
        if (userDtls != null) {
            redirectAttributes.addFlashAttribute("msg", " User registered successfully!");
            return "redirect:/signin"; // go to login page
        } else {
            redirectAttributes.addFlashAttribute("msg", " Registration failed!");
            return "redirect:/register";
        }
    }

   @GetMapping("/prof")
    public String profilePage(Authentication authentication) {
        // Get role of logged in user
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        if (role.equals("ROLE_ADMIN")) {
            return "redirect:/admin"; // Admin page
        } else if (role.equals("ROLE_USER")) {
            return "redirect:/Cuser"; // User page
        }

        return "redirect:/"; // fallback
    }



}
