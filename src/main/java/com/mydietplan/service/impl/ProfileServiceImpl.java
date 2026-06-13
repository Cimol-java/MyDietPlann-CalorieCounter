package com.mydietplan.service.impl;

import com.mydietplan.exception.AppException;
import com.mydietplan.model.ActivityLevel;
import com.mydietplan.model.Gender;
import com.mydietplan.model.Profile;
import com.mydietplan.repository.ProfileRepository;
import com.mydietplan.service.ProfileService;
import com.mydietplan.util.IdGenerator;

public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    private void validateProfileData(String name, int age, double weightKg, double heightCm) {
        if (name == null || name.trim().isEmpty()) {
            throw new AppException("Nama tidak boleh kosong.");
        }
        if (age < 1 || age > 120) {
            throw new AppException("Usia harus antara 1 dan 120 tahun.");
        }
        if (weightKg < 1 || weightKg > 500) {
            throw new AppException("Berat badan harus antara 1 dan 500 kg.");
        }
        if (heightCm < 30 || heightCm > 300) {
            throw new AppException("Tinggi badan harus antara 30 dan 300 cm.");
        }
    }

    @Override
    public boolean createProfile(String userId, String name, int age, Gender gender,
                                  double weightKg, double heightCm, ActivityLevel activityLevel) {
        validateProfileData(name, age, weightKg, heightCm);
        if (profileRepository.existsByUserId(userId)) {
            return false;
        }
        String id = IdGenerator.generate();
        Profile profile = new Profile(id, userId, name.trim(), age, gender, weightKg, heightCm, activityLevel);
        profileRepository.save(profile);
        return true;
    }

    @Override
    public boolean updateProfile(String userId, String name, int age, Gender gender,
                                  double weightKg, double heightCm, ActivityLevel activityLevel) {
        validateProfileData(name, age, weightKg, heightCm);
        Profile profile = profileRepository.findByUserId(userId);
        if (profile == null) {
            return false;
        }
        profile.setName(name.trim());
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
