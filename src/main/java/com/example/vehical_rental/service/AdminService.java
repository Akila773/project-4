package com.example.vehical_rental.service;

import com.example.vehical_rental.model.UserDtls;

public interface AdminService {

    public boolean checkEmail(String email);
    public UserDtls createadmin(UserDtls admin);

}
