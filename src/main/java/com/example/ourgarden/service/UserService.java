package com.example.ourgarden.service;

import com.example.ourgarden.model.entity.OrderEntity;
import com.example.ourgarden.model.entity.UserEntity;
import com.example.ourgarden.model.service.UserEntityServiceModel;

public interface UserService {

    void initAdmin();

    UserEntity findByPhoneNumber(String phoneNumber);

    UserEntity findByUsername(String username);

    void registerUser(UserEntityServiceModel userEntityServiceModel);


    void addOrder(OrderEntity order);
}
