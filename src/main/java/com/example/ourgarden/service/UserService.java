package com.example.ourgarden.service;

import com.example.ourgarden.model.entity.OrderEntity;
import com.example.ourgarden.model.entity.UserEntity;
import com.example.ourgarden.model.service.UserEntityServiceModel;
import com.example.ourgarden.model.view.UserViewModel;

import java.util.List;

public interface UserService {

    void initUsers();

    UserEntity findByPhoneNumber(String phoneNumber);

    UserEntity findByUsername(String username);

    void registerUser(UserEntityServiceModel userEntityServiceModel);


    void addOrder(OrderEntity order);

    String getNames(String name);

    void removeOrder(OrderEntity order);

    List<UserViewModel> findAllActive();

    List<UserViewModel> findAllInActive();

    void changeStatus(Long id);

    UserViewModel findUserById(Long id);

    void editRegisteredUser(UserViewModel user, Long id);
}
