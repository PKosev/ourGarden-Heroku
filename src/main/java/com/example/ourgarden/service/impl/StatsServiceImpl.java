package com.example.ourgarden.service.impl;

import com.example.ourgarden.model.view.StatsViewModel;
import com.example.ourgarden.service.StatsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class StatsServiceImpl implements StatsService {
    private int anonymousRequests, authRequests;

    @Override
    public void onRequest() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if(authentication.getPrincipal() instanceof UserDetails){
                authRequests++;
            }else {
                anonymousRequests++;
            }
    }

    @Override
    public StatsViewModel getStats() {
        return new StatsViewModel(authRequests,anonymousRequests);
    }
}
