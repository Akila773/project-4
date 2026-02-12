package com.example.vehical_rental.service;

import com.example.vehical_rental.model.UserDtls;

public interface UserService {

    public UserDtls createUser(UserDtls user);
    public boolean checkEmail(String email);

}
