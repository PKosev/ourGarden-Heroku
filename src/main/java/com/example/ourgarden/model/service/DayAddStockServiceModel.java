package com.example.ourgarden.model.service;

import com.example.ourgarden.model.entity.OrderEntity;
import com.example.ourgarden.model.entity.enums.ProductNameEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public class DayAddStockServiceModel {
    private Long id;
    private LocalDate date;
    private ProductNameEnum productNameEnum;
    private BigDecimal quantity;
    private BigDecimal reservedQuantity;
    private BigDecimal minQuantity;
    private BigDecimal maxQuantity;
    private Boolean isActive;
    private Set<OrderEntity> orders;

    public DayAddStockServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ProductNameEnum getProductNameEnum() {
        return productNameEnum;
    }

    public void setProductNameEnum(ProductNameEnum productNameEnum) {
        this.productNameEnum = productNameEnum;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(BigDecimal reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }

    public BigDecimal getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(BigDecimal minQuantity) {
        this.minQuantity = minQuantity;
    }

    public BigDecimal getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(BigDecimal maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Set<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderEntity> orders) {
        this.orders = orders;
    }
}
