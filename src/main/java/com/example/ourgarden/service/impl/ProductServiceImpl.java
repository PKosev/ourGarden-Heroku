package com.example.ourgarden.service.impl;

import com.example.ourgarden.model.entity.ProductEntity;
import com.example.ourgarden.model.entity.enums.ProductNameEnum;
import com.example.ourgarden.repository.ProductRepository;
import com.example.ourgarden.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void initProducts() {
            if(productRepository.count() != 0){
                return;
            }
            Arrays.stream(ProductNameEnum.values())
                .forEach(categoryNameEnum -> {
                    ProductEntity product = new ProductEntity();
                    product.setProductNameEnum(categoryNameEnum);
                    switch (categoryNameEnum){
                        case HONEY -> {
                            product.setNameInBulgarian("Мед");
                            product.setPricePerKilogram(BigDecimal.valueOf(10));
                        }
                        case POTATO-> {
                            product.setNameInBulgarian("Картофи");
                            product.setPricePerKilogram(BigDecimal.valueOf(0.8));
                        }
                        case TOMATO -> {
                            product.setNameInBulgarian("Домати");
                            product.setPricePerKilogram(BigDecimal.valueOf(2));
                        }
                        case CABBAGE -> {
                            product.setNameInBulgarian("Зеле");
                            product.setPricePerKilogram(BigDecimal.valueOf(1));
                        }
                        case SAFFRON -> {
                            product.setNameInBulgarian("Шафран");
                            product.setPricePerKilogram(BigDecimal.valueOf(3000));
                        }
                        case CUCUMBER -> {
                            product.setNameInBulgarian("Краставици");
                            product.setPricePerKilogram(BigDecimal.valueOf(2));
                        }
                        case ZUCCHINI -> {
                            product.setNameInBulgarian("Тикивички");
                            product.setPricePerKilogram(BigDecimal.valueOf(2));
                        }
                        case SWEET_CORN-> {
                            product.setNameInBulgarian("Сладка царевица");
                            product.setPricePerKilogram(BigDecimal.valueOf(1.5));
                        }
                        case EARTH_APPLE-> {
                            product.setNameInBulgarian("Земна ябълка");
                            product.setPricePerKilogram(BigDecimal.valueOf(8));
                        }
                    }
                    productRepository.save(product);
                });
    }

    @Override
    public Optional<ProductEntity> findByProductNameEnum(ProductNameEnum productNameEnum) {
        return productRepository.findByProductNameEnum(productNameEnum);
    }
}
