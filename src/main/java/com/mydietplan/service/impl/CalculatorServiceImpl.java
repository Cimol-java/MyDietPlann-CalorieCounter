package com.mydietplan.service.impl;

import com.mydietplan.model.Profile;
import com.mydietplan.model.Gender;
import com.mydietplan.service.CalculatorService;

public class CalculatorServiceImpl implements CalculatorService {

    @Override
    public double calculateBMR(Profile profile) {
        double weight = profile.getWeightKg();
        double height = profile.getHeightCm();
        int age = profile.getAge();

        double bmr = (10 * weight) + (6.25 * height) - (5 * age);

        if (profile.getGender() == Gender.MALE) {
            bmr += 5;
        } else {
            bmr -= 161;
        }
        return bmr;
    }

    @Override
    public double calculateTDEE(Profile profile) {
        double bmr = calculateBMR(profile);
        return bmr * profile.getActivityLevel().getFactor();
    }

    @Override
    public double calculateProtein(Profile profile) {
        return profile.getWeightKg() * 1.0;
    }

    @Override
    public double calculateWater(Profile profile) {
        return profile.getWeightKg() * 35; // dalam mL
    }
}
