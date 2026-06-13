package com.mydietplan.model;

import java.time.LocalDateTime;

public class WaterLog extends BaseLog {
    private double amountMl;

    public WaterLog(String id, String userId, LocalDateTime loggedAt, double amountMl) {
        super(id, userId, loggedAt);
        this.amountMl = amountMl;
    }

    public double getAmountMl() {
        return amountMl;
    }

    public void setAmountMl(double amountMl) {
        this.amountMl = amountMl;
    }
}
