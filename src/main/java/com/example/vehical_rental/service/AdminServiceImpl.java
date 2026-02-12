package com.example.vehical_rental.service;

import com.example.vehical_rental.model.UserDtls;
import com.example.vehical_rental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository adminRepo;

    @Override
    public boolean checkEmail(String email) {
        return adminRepo.existsByEmail(email);
    }

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDtls createadmin(UserDtls admin) {
        // Encode password
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        // Default role if not set
        if (admin.getRole() == null || admin.getRole().isEmpty()) {
            admin.setRole("ROLE_STAFF_ADMIN");
        }
        return adminRepo.save(admin);
    }

}
