package com.example.vehical_rental.repository;


import com.example.vehical_rental.model.UserDtls;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserDtls, Integer> {
    public boolean existsByEmail(String email);
    public UserDtls findByEmail(String email);
    public UserDtls findById(int id);
    List<UserDtls> findByRole(String role);
}
