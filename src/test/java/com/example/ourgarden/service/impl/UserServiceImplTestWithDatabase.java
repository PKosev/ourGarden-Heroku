package com.example.ourgarden.service.impl;

import com.example.ourgarden.model.binding.DayAddStockBindingModel;
import com.example.ourgarden.model.binding.OrderBindingModel;
import com.example.ourgarden.model.entity.OrderEntity;
import com.example.ourgarden.model.entity.UserEntity;
import com.example.ourgarden.model.entity.enums.UserRoleEnum;
import com.example.ourgarden.model.service.UserEntityServiceModel;
import com.example.ourgarden.model.view.DayViewModel;
import com.example.ourgarden.model.view.UserViewModel;
import com.example.ourgarden.repository.UserRepository;
import com.example.ourgarden.service.DayService;
import com.example.ourgarden.service.OrderService;
import com.example.ourgarden.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.example.ourgarden.model.entity.enums.ProductNameEnum.TOMATO;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceImplTestWithDatabase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DayService dayService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @BeforeEach
    void SetUp(){
        initDayEntry();
        createSomeOrders();
    }



    @Test
    void registerUser() {
        UserEntityServiceModel userEntityServiceModel = initUserEntityServiceModel();

        userService.registerUser(userEntityServiceModel);
        UserEntity userEntity = userRepository.findById(4L).orElse(null);
        assertEquals(true,userEntity.getActive());
        userRepository.deleteById(4L);
    }

    @Test
    void changeStatus() {
        UserEntity userEntity1 = userRepository.findById(3L).orElse(null);
        userService.changeStatus(3L);
        UserEntity userEntity2 = userRepository.findById(3L).orElse(null);
        assertEquals(userEntity1.getActive(), !userEntity2.getActive());
    }

    @Test
    void editRegisteredUser() {
        UserEntityServiceModel userEntityServiceModel = initUserEntityServiceModel();
        userService.registerUser(userEntityServiceModel);

        UserEntity userEntity = userService.findByUsername("test");
        userEntity.setUserRoleEnum(UserRoleEnum.USER);
        userService.editRegisteredUser(modelMapper.map(userEntity, UserViewModel.class),5L);


        userEntity = userRepository.findByUsername("test").orElse(null);
        assertEquals(UserRoleEnum.USER,userEntity.getUserRoleEnum());
        userRepository.deleteById(5L);
    }

    private UserEntityServiceModel initUserEntityServiceModel() {
        UserEntityServiceModel testUserEntityServiceModel = new UserEntityServiceModel();
        testUserEntityServiceModel.setFirstName("Test");
        testUserEntityServiceModel.setLastName("Tests");
        testUserEntityServiceModel.setActive(true);
        testUserEntityServiceModel.setUsername("test");
        testUserEntityServiceModel.setPassword("test");
        testUserEntityServiceModel.setUserRoleEnum(UserRoleEnum.ADMIN);
        testUserEntityServiceModel.setAddress("Test Street");
        testUserEntityServiceModel.setDateCreated(LocalDate.now());
        testUserEntityServiceModel.setId(4L);
        testUserEntityServiceModel.setPhoneNumber("0870000000");
        return testUserEntityServiceModel;
    }

    private void createSomeOrders() {
        DayViewModel dayViewModel = dayService.findByID(1L);

        OrderBindingModel orderInfo = new OrderBindingModel();
        orderInfo.setQuantity(BigDecimal.valueOf(30));
        orderInfo.setComment("Test");
        orderInfo.setIdentity("user");
        orderService.addOffer(dayViewModel,orderInfo);

        //adding 2nd order
        OrderBindingModel orderInfo2 = new OrderBindingModel();
        orderInfo2.setQuantity(BigDecimal.valueOf(30));
        orderInfo2.setComment("Test");
        orderInfo2.setIdentity("0876673562");
        orderService.addOrderByNumber(dayViewModel, orderInfo2);
        //re-adding for some additional coverage
        orderService.addOrderByNumber(dayViewModel, orderInfo2);


    }

    private void initDayEntry() {
        DayAddStockBindingModel dayAddStockBindingModel = new DayAddStockBindingModel();
        dayAddStockBindingModel.setDate(LocalDate.of(2021,12, 5));
        dayAddStockBindingModel.setQuantity(BigDecimal.valueOf(200));
        dayAddStockBindingModel.setProductNameEnum(TOMATO);
        dayService.addOrCreate(dayAddStockBindingModel);
        //make another 200 quantity more cases covered
        dayService.addOrCreate(dayAddStockBindingModel);
    }
}