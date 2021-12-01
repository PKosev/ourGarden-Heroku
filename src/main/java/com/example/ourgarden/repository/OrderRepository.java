package com.example.ourgarden.repository;

import com.example.ourgarden.model.entity.DayEntity;
import com.example.ourgarden.model.entity.OrderEntity;
import com.example.ourgarden.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findAllByUser_UsernameAndDayEntity_DateAfter(String name, LocalDate minusDays);

    OrderEntity findByUserAndDayEntity(UserEntity user, DayEntity dayEntity);

    List<OrderEntity> findAllByDayEntity_DateAfter(LocalDate minusDays);

    List<OrderEntity> findAllByReadyAndDayEntity_DateAfter(Boolean ready, LocalDate dayEntity_date);


    List<OrderEntity> findAllByDayEntity_DateBefore(LocalDate minusDays);
}
