package com.mydietplan.model;

import java.time.LocalDateTime;

public abstract class BaseLog extends BaseEntity {
    private String userId;
    private LocalDateTime loggedAt;

    public BaseLog(String id, String userId, LocalDateTime loggedAt) {
        super(id);
        this.userId = userId;
        this.loggedAt = loggedAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getLoggedAt() {
        return loggedAt;
    }

    public void setLoggedAt(LocalDateTime loggedAt) {
        this.loggedAt = loggedAt;
    }
}
