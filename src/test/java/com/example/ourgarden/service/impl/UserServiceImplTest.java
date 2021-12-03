package com.example.ourgarden.service.impl;

import com.example.ourgarden.model.entity.UserEntity;
import com.example.ourgarden.model.entity.enums.UserRoleEnum;
import com.example.ourgarden.model.service.UserEntityServiceModel;
import com.example.ourgarden.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
class UserServiceImplTest {

    private UserServiceImpl userService;


    @Mock
    private ModelMapper mockModelMapper;
    @Mock
    private PasswordEncoder mockPasswordEncoder;
    @Mock
    private UserRepository mockUserRepository;

    UserEntity testUser;
    @BeforeEach
    void init(){
        userService = new UserServiceImpl(mockUserRepository,mockModelMapper,mockPasswordEncoder);
        testUser = new UserEntity();
        testUser.setFirstName("Test");
        testUser.setLastName("Tests");
        testUser.setActive(true);
        testUser.setUsername("test");
        testUser.setPassword("test");
        testUser.setUserRoleEnum(UserRoleEnum.ADMIN);
        testUser.setAddress("Test Street");
        testUser.setDateCreated(LocalDate.now());
        testUser.setId(4L);
    }

    @Test
    void UserNotFound() {
        assertNull(userService.findByUsername("invalid_username"));
    }

    @Test
    void UserFound() {
        Mockito.when(mockUserRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.ofNullable(testUser));
        UserEntity actual = userService.findByUsername("test");
        assertEquals(actual.getUsername(), testUser.getUsername());
    }

    @Test
    void UserShowBothNames(){
        Mockito.when(mockUserRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.ofNullable(testUser));
        UserEntity actual = userService.findByUsername("test");
        String names = userService.getNames(testUser.getUsername());

        assertEquals(names,testUser.getFirstName() + " " + testUser.getLastName());
    }
}