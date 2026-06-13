package com.mydietplan.service.impl;

import com.mydietplan.model.Profile;
import com.mydietplan.model.Gender;
import com.mydietplan.model.ActivityLevel;
import com.mydietplan.repository.ProfileRepository;
import com.mydietplan.service.ProfileService;
import com.mydietplan.util.IdGenerator;

public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public boolean createProfile(String userId, String name, int age, Gender gender, double weightKg, double heightCm, ActivityLevel activityLevel) {
        if (profileRepository.existsByUserId(userId)) {
            return false;
        }
        String id = IdGenerator.generate();
        Profile profile = new Profile(id, userId, name, age, gender, weightKg, heightCm, activityLevel);
        profileRepository.save(profile);
        return true;
    }

    @Override
    public boolean updateProfile(String userId, String name, int age, Gender gender, double weightKg, double heightCm, ActivityLevel activityLevel) {
        Profile profile = profileRepository.findByUserId(userId);
        if (profile == null) {
            return false;
        }
        profile.setName(name);
        profile.setAge(age);
        profile.setGender(gender);
        profile.setWeightKg(weightKg);
        profile.setHeightCm(heightCm);
        profile.setActivityLevel(activityLevel);
        profileRepository.save(profile);
        return true;
    }

    @Override
    public Profile getProfile(String userId) {
        return profileRepository.findByUserId(userId);
    }
}
