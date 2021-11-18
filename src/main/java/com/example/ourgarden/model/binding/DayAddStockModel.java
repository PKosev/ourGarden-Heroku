package com.example.ourgarden.model.binding;

import com.example.ourgarden.model.entity.ProductEntity;
import com.example.ourgarden.model.entity.enums.ProductNameEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public class DayAddStockModel {

    private LocalDate date;
    private ProductNameEnum productNameEnum;
    private BigDecimal quantity;

    public DayAddStockModel() {
    }

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @NotNull
    public ProductNameEnum getProductNameEnum() {
        return productNameEnum;
    }

    public void setProductNameEnum(ProductNameEnum productNameEnum) {
        this.productNameEnum = productNameEnum;
    }

    @NotNull
    @Positive
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
