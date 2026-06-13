package com.mydietplan.model;

import java.time.LocalDateTime;

public class FoodLog extends BaseLog {
    private String foodName;
    private double calories;
    private double protein;

    public FoodLog(String id, String userId, LocalDateTime loggedAt, String foodName, double calories, double protein) {
        super(id, userId, loggedAt);
        this.foodName = foodName;
        this.calories = calories;
        this.protein = protein;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }
}
