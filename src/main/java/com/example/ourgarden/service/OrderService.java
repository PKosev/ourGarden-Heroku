package com.example.ourgarden.service;

import com.example.ourgarden.model.binding.OrderBindingModel;
import com.example.ourgarden.model.entity.DayEntity;

public interface OrderService {
    void addOffer(String name, DayEntity dayEntity, OrderBindingModel orderBindingModel);
}
