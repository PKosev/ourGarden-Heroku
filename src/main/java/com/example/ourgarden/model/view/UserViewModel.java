package com.example.ourgarden.model.view;

import com.example.ourgarden.model.entity.OrderEntity;
import com.example.ourgarden.model.entity.enums.UserRoleEnum;

import java.time.LocalDate;
import java.util.Set;

public class UserViewModel {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String userRoleEnum;
    private LocalDate dateCreated;
    private Boolean isActive;
    private String address;
    private String phoneNumber;

    public UserViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserRoleEnum() {
        return userRoleEnum;
    }

    public void setUserRoleEnum(String userRoleEnum) {
        this.userRoleEnum = userRoleEnum;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
