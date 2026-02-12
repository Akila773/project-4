package com.example.vehical_rental.service;




import com.example.vehical_rental.model.Vehicale;
import com.example.vehical_rental.repository.VehicaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicaleServerImpl implements VehicaleService {


    @Autowired
    private VehicaleRepository vehicaleRepo;

    @Override
    public Vehicale createVehicale(Vehicale vehicale) {
        return vehicaleRepo.save(vehicale);
    }



}
