package com.example.ourgarden.service;

import com.example.ourgarden.model.binding.OrderBindingModel;
import com.example.ourgarden.model.view.DayViewModel;
import com.example.ourgarden.model.view.OrderViewModel;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    void addOffer(DayViewModel dayEntity, OrderBindingModel orderBindingModel);

    List<OrderViewModel> findByUserNameAndDateAfter(String name, LocalDate minusDays);

    void removeOrder(Long id);

    OrderViewModel findById(Long id);

    void addComment(Long id, String comment, String username);

    void addOrderByNumber(DayViewModel dayEntity, OrderBindingModel orderBindingModel);

    List<OrderViewModel> findAllByDateAfter(LocalDate minusDays);

    void updateStatus(Long id);

    List<OrderViewModel> findAllByDateAfterAndNotReady(LocalDate minusDays);

    void removeAllOrdersThatAreExpired();
}
