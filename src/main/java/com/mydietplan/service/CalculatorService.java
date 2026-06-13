package com.mydietplan.service;

import com.mydietplan.model.Profile;

public interface CalculatorService {
    double calculateBMR(Profile profile);
    double calculateTDEE(Profile profile);
    double calculateProtein(Profile profile);
    double calculateWater(Profile profile);
}
