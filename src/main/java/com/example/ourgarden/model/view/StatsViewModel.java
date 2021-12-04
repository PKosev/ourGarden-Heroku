package com.example.ourgarden.model.view;

public class StatsViewModel {
    int authRequests;
    int anonymousRequests;

    public StatsViewModel(int authRequests, int anonymousRequests) {
        this.authRequests = authRequests;
        this.anonymousRequests = anonymousRequests;
    }

    public int getTotalRequests() {
        return this.authRequests + anonymousRequests;
    }

    public int getAuthRequests() {
        return authRequests;
    }

    public int getAnonymousRequests() {
        return anonymousRequests;
    }
}
