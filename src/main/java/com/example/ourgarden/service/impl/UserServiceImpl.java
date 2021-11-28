package com.example.ourgarden.service.impl;

import com.example.ourgarden.model.entity.BaseEntity;
import com.example.ourgarden.model.entity.OrderEntity;
import com.example.ourgarden.model.entity.UserEntity;
import com.example.ourgarden.model.entity.enums.UserRoleEnum;
import com.example.ourgarden.model.service.UserEntityServiceModel;
import com.example.ourgarden.model.view.UserViewModel;
import com.example.ourgarden.repository.UserRepository;
import com.example.ourgarden.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void initUsers() {
        if(userRepository.count() !=0){
            return;
        }
        UserEntity admin = new UserEntity();
        admin.setDateCreated(LocalDate.now());
        admin.setFirstName("Админ");
        admin.setLastName("Админов");
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setUserRoleEnum(UserRoleEnum.ADMIN);
        admin.setAddress("Admin Street 27");
        admin.setPhoneNumber("0876673561");
        admin.setActive(true);
        userRepository.save(admin);
        UserEntity staff = new UserEntity();
        staff.setDateCreated(LocalDate.now());
        staff.setFirstName("Стафф");
        staff.setLastName("Стаффов");
        staff.setUsername("staff");
        staff.setPassword(passwordEncoder.encode("staff"));
        staff.setUserRoleEnum(UserRoleEnum.STAFF);
        staff.setAddress("Staff Street 27");
        staff.setPhoneNumber("0876673562");
        staff.setActive(true);
        userRepository.save(staff);
        UserEntity user = new UserEntity();
        user.setDateCreated(LocalDate.now());
        user.setFirstName("Юзер");
        user.setLastName("Юзеров");
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setUserRoleEnum(UserRoleEnum.USER);
        user.setAddress("User Street 27");
        user.setPhoneNumber("0876673563");
        user.setActive(true);
        userRepository.save(user);
    }

    @Override
    public UserEntity findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).orElse(null);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public void registerUser(UserEntityServiceModel userEntityServiceModel) {
        UserEntity user = modelMapper.map(userEntityServiceModel, UserEntity.class);
        user.setActive(true);
        user.setUserRoleEnum(UserRoleEnum.USER);
        user.setDateCreated(LocalDate.now());
        user.setPassword(passwordEncoder.encode(userEntityServiceModel.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void addOrder(OrderEntity order) {
       UserEntity user = order.getUser();
       user.addOrder(order);
       userRepository.save(user);
    }

    @Override
    public String getNames(String name) {
        UserEntity user = findByUsername(name);
        return user.getFirstName() + " " +user.getLastName();
    }

    @Override
    public void removeOrder(OrderEntity order) {
        UserEntity user = order.getUser();
        user.getOrders().remove(order);
        userRepository.save(user);
    }

    @Override
    public List<UserViewModel> findAllActive() {
        return userRepository.findAllByActive(true).stream().sorted(Comparator.comparing(BaseEntity::getId)).map(user -> modelMapper.map(user, UserViewModel.class)).collect(Collectors.toList());
    }

    @Override
    public List<UserViewModel> findAllInActive() {
        return userRepository.findAllByActive(false).stream().sorted(Comparator.comparing(BaseEntity::getId)).map(user -> modelMapper.map(user, UserViewModel.class)).collect(Collectors.toList());
    }

    @Override
    public void changeStatus(Long id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setActive(!user.getActive());
            userRepository.save(user);
        }
    }

    @Override
    public UserViewModel findUserById(Long id) {
        return userRepository.findById(id).map(user -> modelMapper.map(user,UserViewModel.class)).orElse(null);
    }

    @Override
    public void editRegisteredUser(UserViewModel user, Long id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        if (userEntity != null){
            userEntity.setAddress(user.getAddress());
            userEntity.setFirstName(user.getFirstName());
            userEntity.setLastName(user.getLastName());
            userEntity.setPhoneNumber(user.getPhoneNumber());
            if (!userEntity.getPassword().equals(user.getPassword())){
                userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            switch (user.getUserRoleEnum()) {
                case "ADMIN" -> userEntity.setUserRoleEnum(UserRoleEnum.ADMIN);
                case "STAFF" -> userEntity.setUserRoleEnum(UserRoleEnum.STAFF);
                case "USER" -> userEntity.setUserRoleEnum(UserRoleEnum.USER);
            }
            userRepository.save(userEntity);
        }
    }

}
