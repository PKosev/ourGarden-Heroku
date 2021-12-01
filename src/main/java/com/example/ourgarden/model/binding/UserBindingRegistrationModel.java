package com.example.ourgarden.model.binding;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserBindingRegistrationModel {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;
    private String address;
    private String phoneNumber;
    private Boolean phoneNumberExist;
    private Boolean usernameExist;
    private String userRoleEnum;

    public UserBindingRegistrationModel() {
    }


    @Size(min = 3, max = 15)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Size(min = 2, max = 15)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Size(min = 2, max = 15)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Size(min = 3)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Size(min = 3)
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Size(min = 5)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Pattern(regexp = "08+\\d+")
    @Size(min = 10, max = 10)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getPhoneNumberExist() {
        return phoneNumberExist;
    }

    public void setPhoneNumberExist(Boolean phoneNumberExist) {
        this.phoneNumberExist = phoneNumberExist;
    }

    public Boolean getUsernameExist() {
        return usernameExist;
    }

    public void setUsernameExist(Boolean usernameExist) {
        this.usernameExist = usernameExist;
    }

    public String getUserRoleEnum() {
        return userRoleEnum;
    }

    public void setUserRoleEnum(String userRoleEnum) {
        this.userRoleEnum = userRoleEnum;
    }
}
