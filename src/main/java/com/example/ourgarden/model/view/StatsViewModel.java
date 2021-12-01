package com.example.ourgarden.model.view;

public record StatsViewModel(int authRequests, int anonymousRequests) {

    public int getTotalRequests() {
        return authRequests + anonymousRequests;
    }

    public int getAuthRequests() {
        return authRequests;
    }

    public int getAnonymousRequests() {
        return anonymousRequests;
    }
}
