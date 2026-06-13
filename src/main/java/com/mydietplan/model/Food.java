package com.mydietplan.model;

public class Food extends BaseEntity {
    private String name;
    private double calories;
    private double protein;

    public Food(String id, String name, double calories, double protein) {
        super(id);
        this.name = name;
        this.calories = calories;
        this.protein = protein;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
