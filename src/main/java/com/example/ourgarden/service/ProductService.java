package com.example.ourgarden.service;

import com.example.ourgarden.model.entity.ProductEntity;
import com.example.ourgarden.model.entity.enums.ProductNameEnum;
import com.example.ourgarden.model.view.ProductViewModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    void initProducts();

    Optional<ProductEntity> findByProductNameEnum(ProductNameEnum productNameEnum);

    List<ProductViewModel> findAll();

    void editPrice(Long id, BigDecimal price);
}
