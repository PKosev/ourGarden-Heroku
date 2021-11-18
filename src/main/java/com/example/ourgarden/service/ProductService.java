package com.example.ourgarden.service;

import com.example.ourgarden.model.entity.ProductEntity;
import com.example.ourgarden.model.entity.enums.ProductNameEnum;

import java.util.Optional;

public interface ProductService {
    void initProducts();

    Optional<ProductEntity> findByProductNameEnum(ProductNameEnum productNameEnum);
}
