package com.example.vehical_rental.controller;

import com.example.vehical_rental.model.UserDtls;
import com.example.vehical_rental.model.Vehicale;
import com.example.vehical_rental.repository.UserRepository;
import com.example.vehical_rental.repository.VehicaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/Cuser")
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Show profile page
    @GetMapping("/profile")
    public String showProfile(Authentication auth, Model model) {
        String email = auth.getName();
        UserDtls user = userRepository.findByEmail(email);
        model.addAttribute("user", user);
        return "User-profile";
    }

    // Upload profile picture
    @PostMapping("/upload-profile/{id}")
    public String uploadProfile(@PathVariable int id,
                                @RequestParam("image") MultipartFile file,
                                RedirectAttributes redirectAttributes) {
        try {
            UserDtls user = userRepository.findById(id);

            if (!file.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                String uploadDir = "C:/user-profile-uploads/";

                File uploadPath = new File(uploadDir);
                if (!uploadPath.exists()) uploadPath.mkdirs();

                file.transferTo(new File(uploadDir + fileName));

                user.setProfilePicture(fileName);
                userRepository.save(user);
            }

            redirectAttributes.addFlashAttribute("success", "Profile picture uploaded!");
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to upload image.");
        }

        return "redirect:/Cuser/profile";
    }

    // Delete profile picture
    @PostMapping("/delete-profile/{id}")
    public String deleteProfile(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            UserDtls user = userRepository.findById(id);

            if (user.getProfilePicture() != null) {
                File file = new File("C:/user-profile-uploads/" + user.getProfilePicture());
                if (file.exists()) file.delete();

                user.setProfilePicture(null);
                userRepository.save(user);
            }

            redirectAttributes.addFlashAttribute("success", "Profile picture deleted!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to delete profile picture.");
        }

        return "redirect:/Cuser/profile";
    }

    // Show edit profile page
    @GetMapping("/edit")
    public String editProfile(Authentication authentication, Model model) {
        String email = authentication.getName();
        UserDtls user = userRepository.findByEmail(email);
        model.addAttribute("user", user);
        return "editprofile";
    }

    // Save edited profile
    @PostMapping("/edit")
    public String updateProfile(@ModelAttribute UserDtls updatedUser, Authentication authentication) {
        String email = authentication.getName();
        UserDtls user = userRepository.findByEmail(email);

        user.setFullName(updatedUser.getFullName());
        user.setEmail(updatedUser.getEmail());
        user.setAddress(updatedUser.getAddress());
        user.setPhone(updatedUser.getPhone());

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        userRepository.save(user);
        return "redirect:/Cuser/profile";
    }

    // Delete account
    @GetMapping("/delete")
    public String deleteAccount(Authentication authentication) {
        String email = authentication.getName();
        UserDtls user = userRepository.findByEmail(email);
        userRepository.delete(user);

        return "redirect:/logout";
    }



}
