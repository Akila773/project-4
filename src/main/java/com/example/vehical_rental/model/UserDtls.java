package com.example.vehical_rental.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class UserDtls {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fullName;

    private String email;

    private String address;

    private String phone;

    private String password;

    private String role;

    private LocalDateTime lastLogin;

    private String profilePicture;


}

