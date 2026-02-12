package com.example.vehical_rental.controller;

import com.example.vehical_rental.model.UserDtls;
import com.example.vehical_rental.repository.UserRepository;
import com.example.vehical_rental.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
@RequestMapping("/admin")
public class ttController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminService adminService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    // Show profile
    @GetMapping("/profile")
    public String showProfile(Authentication auth, Model model) {
        String email = auth.getName(); // logged in username (email)
        UserDtls admin = userRepository.findByEmail(email);
        model.addAttribute("admin", admin);
        return "admin-profile";
    }

    @GetMapping("/admin-register")
    public String adminregister(){
        return "admin-register";
    }

    // Update profile
    @PostMapping("/updateProfile")
    public String updateProfile(@ModelAttribute("admin") UserDtls updatedAdmin, Authentication auth) {
        String email = auth.getName();
        UserDtls existingAdmin = userRepository.findByEmail(email);

        if (existingAdmin != null) {
            existingAdmin.setFullName(updatedAdmin.getFullName());
            existingAdmin.setAddress(updatedAdmin.getAddress());


            // Update password only if new one entered
            if (updatedAdmin.getPassword() != null && !updatedAdmin.getPassword().isEmpty()) {
                existingAdmin.setPassword(updatedAdmin.getPassword()); // encode if using BCrypt
            }

            userRepository.save(existingAdmin);
        }

        return "redirect:/admin/profile?success";
    }

    @PostMapping("/createadmin")
    public String createadmin(@ModelAttribute UserDtls admin, RedirectAttributes redirectAttributes) {

        // Check email duplication
        boolean exists = adminService.checkEmail(admin.getEmail());
        if (exists) {
            redirectAttributes.addFlashAttribute("msg", " Email already exists!");
            return "redirect:admin-register";
        }
        // Save user
        UserDtls userDtls = adminService.createadmin(admin);
        if (userDtls != null) {
            redirectAttributes.addFlashAttribute("msg", " User registered successfully!");
            return "redirect:/admin/profile?success";
        } else {
            redirectAttributes.addFlashAttribute("msg", " Registration failed!");
            return "redirect:/admin-register";
        }

    }

    @GetMapping("/users-mn")
    public String viewUsers(Model model) {

        //List<UserDtls> users = userRepository.findAll();
        List<UserDtls> users = userRepository.findByRole("ROLE_USER");

        model.addAttribute("users", users);
        return "user-list";
    }


    @GetMapping("/Ausers-mn")
    public String viewStaffAdmins(Model model) {

        List<UserDtls> admin = userRepository.findByRole("ROLE_STAFF_ADMIN");

        model.addAttribute("admin", admin);
        return "staff-admin-list";  // your Thymeleaf/JSP page
    }


    // Delete user by id
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
        return "redirect:/admin/users-mn?deleted";
    }

    @GetMapping("/deleteAdmin/{id}")
    public String deleteAdmin(@PathVariable int id) {
        userRepository.deleteById(id);
        return "redirect:/admin/Ausers-mn?deleted";
    }
}

