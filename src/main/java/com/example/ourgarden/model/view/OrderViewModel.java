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
    private DayViewModel dayEntity;
    private UserViewModel user;
    private Boolean isReady;
    private List<CommentViewModel> comments;


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

    public DayViewModel getDayEntity() {
        return dayEntity;
    }

    public void setDayEntity(DayViewModel dayEntity) {
        this.dayEntity = dayEntity;
    }

    public UserViewModel getUser() {
        return user;
    }

    public void setUser(UserViewModel user) {
        this.user = user;
    }

    public Boolean getReady() {
        return isReady;
    }

    public void setReady(Boolean ready) {
        isReady = ready;
    }

    public List<CommentViewModel> getComments() {
        return comments;
    }

    public void setComments(List<CommentViewModel> comments) {
        this.comments = comments;
    }
    public void addComment(CommentViewModel comment) {
        this.comments.add(comment);
    }


}
