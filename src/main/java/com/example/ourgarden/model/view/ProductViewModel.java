package com.example.ourgarden.model.view;

import com.example.ourgarden.model.entity.enums.ProductNameEnum;

import java.math.BigDecimal;

public class ProductViewModel {
    private Long id;
    private ProductNameEnum productNameEnum;
    private String nameInBulgarian;
    private BigDecimal pricePerKilogram;

    public ProductViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductNameEnum getProductNameEnum() {
        return productNameEnum;
    }

    public void setProductNameEnum(ProductNameEnum productNameEnum) {
        this.productNameEnum = productNameEnum;
    }

    public String getNameInBulgarian() {
        return nameInBulgarian;
    }

    public void setNameInBulgarian(String nameInBulgarian) {
        this.nameInBulgarian = nameInBulgarian;
    }

    public BigDecimal getPricePerKilogram() {
        return pricePerKilogram;
    }

    public void setPricePerKilogram(BigDecimal pricePerKilogram) {
        this.pricePerKilogram = pricePerKilogram;
    }
}
