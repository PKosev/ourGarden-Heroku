package com.example.ourgarden.service;

import com.example.ourgarden.model.binding.OrderBindingModel;
import com.example.ourgarden.model.entity.DayEntity;
import com.example.ourgarden.model.entity.OrderEntity;
import com.example.ourgarden.model.view.OrderViewModel;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    void addOffer(String name, DayEntity dayEntity, OrderBindingModel orderBindingModel);

    List<OrderViewModel> findByUserNameAndDateAfter(String name, LocalDate minusDays);

    void removeOrder(Long id);

    OrderEntity findById(Long id);

    void addComment(OrderEntity order, String comment, String username);
}
