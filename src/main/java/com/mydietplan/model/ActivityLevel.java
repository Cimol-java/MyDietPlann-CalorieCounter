package com.mydietplan.model;

public enum ActivityLevel {
    VERY_LIGHT(1.2),
    LIGHT(1.375),
    MODERATE(1.55),
    HEAVY(1.725),
    VERY_HEAVY(1.9);

    private final double factor;

    ActivityLevel(double factor) {
        this.factor = factor;
    }

    public double getFactor() {
        return factor;
    }
}
