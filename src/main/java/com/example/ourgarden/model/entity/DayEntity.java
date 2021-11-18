package com.example.ourgarden.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "days",uniqueConstraints = @UniqueConstraint(columnNames = {"date", "product_id"}))
public class DayEntity extends BaseEntity {

    private LocalDate date;
    private ProductEntity product;
    private BigDecimal quantity;
    private BigDecimal reservedQuantity;
    private BigDecimal minQuantity;
    private BigDecimal maxQuantity;
    private Boolean isActive;
    private Set<OrderEntity> orders;

    public DayEntity() {
        this.orders = new HashSet<>();
    }
    @Column(name = "date")
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @ManyToOne
    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
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
            this.isActive = active;
    }

    @OneToMany(fetch = FetchType.EAGER)
    public Set<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderEntity> orders) {
        this.orders = orders;
    }

    public void addOrder(OrderEntity order){
        this.orders.add(order);
    }
}
