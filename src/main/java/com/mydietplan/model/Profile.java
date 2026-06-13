package com.mydietplan.model;

public class Profile extends BaseEntity {
    private String userId;
    private String name;
    private int age;
    private Gender gender;
    private double weightKg;
    private double heightCm;
    private ActivityLevel activityLevel;

    public Profile(String id, String userId, String name, int age, Gender gender, double weightKg, double heightCm, ActivityLevel activityLevel) {
        super(id);
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.weightKg = weightKg;
        this.heightCm = heightCm;
        this.activityLevel = activityLevel;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(double weightKg) {
        this.weightKg = weightKg;
    }

    public double getHeightCm() {
        return heightCm;
    }

    public void setHeightCm(double heightCm) {
        this.heightCm = heightCm;
    }

    public ActivityLevel getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(ActivityLevel activityLevel) {
        this.activityLevel = activityLevel;
    }
}
