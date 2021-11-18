package com.example.ourgarden.model.entity;

import com.example.ourgarden.model.entity.enums.ProductNameEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class ProductEntity extends BaseEntity{

    private ProductNameEnum productNameEnum;
    private String nameInBulgarian;
    private BigDecimal pricePerKilogram;

    public ProductEntity() {
    }
    @Enumerated(EnumType.STRING)
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
