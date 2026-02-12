package com.example.vehical_rental.controller;

import com.example.vehical_rental.model.Vehicale;
import com.example.vehical_rental.repository.UserRepository;
import com.example.vehical_rental.repository.VehicaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("Cuser")
public class VehicalUsweControlle {

    @Autowired
    private VehicaleRepository vehicaleRepository ;

    @Autowired
    private UserRepository userRepository ;



    @GetMapping("/vehicalViwe")
    public String vehicalViwe( Model model) {

        List<Vehicale> vehicles = vehicaleRepository.findAll();
        model.addAttribute("vehicles", vehicles);
        return "vehicalViwe";  // Thymeleaf template
    }




}
