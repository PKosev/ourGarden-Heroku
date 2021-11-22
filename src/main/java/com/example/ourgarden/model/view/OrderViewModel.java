package com.example.ourgarden.model.view;

import com.example.ourgarden.model.entity.CommentEntity;
import com.example.ourgarden.model.entity.DayEntity;
import com.example.ourgarden.model.entity.UserEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderViewModel {
    private Long id;
    private LocalDate date;
    private BigDecimal quantity;
    private DayEntity dayEntity;
    private UserEntity user;
    private List<CommentEntity> comments;


    public OrderViewModel() {
        this.comments = new ArrayList<>();
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

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public DayEntity getDayEntity() {
        return dayEntity;
    }

    public void setDayEntity(DayEntity dayEntity) {
        this.dayEntity = dayEntity;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }
    public void addComment(CommentEntity comment) {
        this.comments.add(comment);
    }


}
