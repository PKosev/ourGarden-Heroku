package com.example.ourgarden.service.impl;

import com.example.ourgarden.model.entity.ProductEntity;
import com.example.ourgarden.model.entity.enums.ProductNameEnum;
import com.example.ourgarden.model.view.ProductViewModel;
import com.example.ourgarden.repository.ProductRepository;
import com.example.ourgarden.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
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
                        case HONEY:
                            product.setNameInBulgarian("Мед");
                            product.setPricePerKilogram(BigDecimal.valueOf(10));
                            break;
                        case POTATO:
                            product.setNameInBulgarian("Картофи");
                            product.setPricePerKilogram(BigDecimal.valueOf(0.8));
                         break;
                        case TOMATO :
                            product.setNameInBulgarian("Домати");
                            product.setPricePerKilogram(BigDecimal.valueOf(2));
                            break;
                        case CABBAGE :
                            product.setNameInBulgarian("Зеле");
                            product.setPricePerKilogram(BigDecimal.valueOf(1));
                            break;
                        case SAFFRON :
                            product.setNameInBulgarian("Шафран");
                            product.setPricePerKilogram(BigDecimal.valueOf(3000));
                            break;
                        case CUCUMBER :
                            product.setNameInBulgarian("Краставици");
                            product.setPricePerKilogram(BigDecimal.valueOf(2));
                            break;
                        case ZUCCHINI :
                            product.setNameInBulgarian("Тикивички");
                            product.setPricePerKilogram(BigDecimal.valueOf(2));
                            break;
                        case SWEET_CORN :
                            product.setNameInBulgarian("Сладка царевица");
                            product.setPricePerKilogram(BigDecimal.valueOf(1.5));
                            break;
                        case EARTH_APPLE :
                            product.setNameInBulgarian("Земна ябълка");
                            product.setPricePerKilogram(BigDecimal.valueOf(8));
                            break;
                    }
                    productRepository.save(product);
                });
    }

    @Override
    public Optional<ProductEntity> findByProductNameEnum(ProductNameEnum productNameEnum) {
        return productRepository.findByProductNameEnum(productNameEnum);
    }

    @Override
    public List<ProductViewModel> findAll() {
       return productRepository.findAll().stream().map(product -> modelMapper.map(product, ProductViewModel.class)).collect(Collectors.toList());
    }

    @Override
    public void editPrice(Long id, BigDecimal price) {
        ProductEntity product = productRepository.findById(id).orElse(null);
        assert product != null;
        product.setPricePerKilogram(price);
        productRepository.save(product);
    }
}
