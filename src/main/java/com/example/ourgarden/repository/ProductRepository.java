package com.example.ourgarden.repository;

import com.example.ourgarden.model.entity.ProductEntity;
import com.example.ourgarden.model.entity.enums.ProductNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByProductNameEnum(ProductNameEnum productNameEnum);
}
