package com.example.ourgarden.service;

import com.example.ourgarden.model.entity.DayEntity;
import com.example.ourgarden.model.entity.enums.ProductNameEnum;
import com.example.ourgarden.model.service.DayAddStockServiceModel;
import com.example.ourgarden.model.view.DayViewModel;

import java.time.LocalDate;
import java.util.List;

public interface DayService {
    void addStock(DayAddStockServiceModel dayAddStockServiceModel);

    DayEntity findByDateAnDProduct(LocalDate date, ProductNameEnum productNameEnum);

    void save(DayEntity dayEntity);

    void calculateVariables(DayEntity dayEntity);

    List<DayViewModel> findByDate(LocalDate now);
}
