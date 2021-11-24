package com.example.ourgarden.repository;

import com.example.ourgarden.model.entity.UserEntity;
import com.example.ourgarden.model.view.UserViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
    Optional<UserEntity> findByUsernameAndActive(String username,Boolean active);
    Optional<UserEntity> findByUsername(String username);

    List<UserEntity> findAllByActive(Boolean active);
}
