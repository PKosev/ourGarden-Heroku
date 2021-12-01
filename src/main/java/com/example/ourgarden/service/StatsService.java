package com.example.ourgarden.service;

import com.example.ourgarden.model.view.StatsViewModel;

public interface StatsService {
    void onRequest();
    StatsViewModel getStats();
}
