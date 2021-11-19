package com.example.ourgarden.repository;

import com.example.ourgarden.model.entity.DayEntity;
import com.example.ourgarden.model.entity.enums.ProductNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface DayRepository extends JpaRepository<DayEntity, Long> {
    DayEntity findByDateAndProduct_ProductNameEnum(LocalDate date, ProductNameEnum productNameEnum);

    List<DayEntity> findByDateAfter(LocalDate date);

    List<DayEntity> findByDateAfterAndActive(LocalDate now, boolean b);
}
