package com.example.vehical_rental.controller;


import com.example.vehical_rental.model.Vehicale;
import com.example.vehical_rental.repository.VehicaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Controller
@RequestMapping("/admin")
public class VehicaleAddminCotroller {

    @Autowired
    private VehicaleRepository vehicaleRepository;



    private final String UPLOAD_DIR = "uploads/";

    // Show all vehicles
    @GetMapping("/adminVhicales")
    public String vehicashow(Model model) {
        model.addAttribute("vehicle", vehicaleRepository.findAll());
        return "shvehicales";
    }

    @GetMapping("/vehicleForm")
    public String vehicleForm(Model model) {
        model.addAttribute("vehicle", new Vehicale());
        return "vehicleForm";
    }

    @GetMapping( "/edit/{id}")
    public String vehicleedite(@PathVariable(required = false) Integer id, Model model) {
        if (id != null) {
            Vehicale veh = vehicaleRepository.findById(id).orElse(new Vehicale());
            model.addAttribute("vehicle", veh);
        } else {
            model.addAttribute("vehicle", new Vehicale());
        }
        return "editVForm";
    }


    // Create new vehicle
    @PostMapping("/createvehicle")
    public String createvehicale(@ModelAttribute Vehicale vehicale,
                                 @RequestParam("image") MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Files.copy(file.getInputStream(), uploadPath.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING);

            vehicale.setPhoto(fileName); // store filename in DB
        }

        vehicaleRepository.save(vehicale);
        return "redirect:/admin/adminVhicales";
    }

    // Update existing vehicle
    @PostMapping("/update/{id}")
    public String updateVehicale(@PathVariable("id") int id,
                                 @ModelAttribute Vehicale vehicale,
                                 @RequestParam("image") MultipartFile file) throws IOException {

        Vehicale existing = vehicaleRepository.findById(id).orElse(null);

        if (existing != null) {
            existing.setBrand(vehicale.getBrand());
            existing.setModel(vehicale.getModel());
            existing.setNumberplate(vehicale.getNumberplate());

            if (!file.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Files.copy(file.getInputStream(), uploadPath.resolve(fileName),
                        StandardCopyOption.REPLACE_EXISTING);

                existing.setPhoto(fileName); // update photo
            }

            vehicaleRepository.save(existing);
        }

        return "redirect:/admin/adminVhicales";
    }

    // Delete vehicle
    @GetMapping("/delete/{id}")
    public String deleteVehicale(@PathVariable("id") int id) {
        try {

            
            // Then delete the vehicle
            vehicaleRepository.deleteById(id);
            
            return "redirect:/admin/adminVhicales?deleted=true";
        } catch (Exception e) {
            // Log the error and redirect with error message
            System.err.println("Error deleting vehicle: " + e.getMessage());
            return "redirect:/admin/adminVhicales?error=true";
        }
    }
}
