package com.example.vehical_rental.repository;



import com.example.vehical_rental.model.Vehicale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicaleRepository extends JpaRepository<Vehicale, Integer> {
    public Vehicale findByid(int id);
}

