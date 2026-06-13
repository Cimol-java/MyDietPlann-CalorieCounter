package com.mydietplan.service;

import com.mydietplan.model.Profile;
import com.mydietplan.model.Gender;
import com.mydietplan.model.ActivityLevel;

public interface ProfileService {
    boolean createProfile(String userId, String name, int age, Gender gender, double weightKg, double heightCm, ActivityLevel activityLevel);
    boolean updateProfile(String userId, String name, int age, Gender gender, double weightKg, double heightCm, ActivityLevel activityLevel);
    Profile getProfile(String userId);
}
