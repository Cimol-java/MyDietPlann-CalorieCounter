package com.mydietplan.repository;

import com.mydietplan.model.Profile;
import java.util.HashMap;

public class ProfileRepository {
    private final HashMap<String, Profile> storage = new HashMap<>();

    public void save(Profile profile) {
        storage.put(profile.getUserId(), profile);
    }

    public Profile findByUserId(String userId) {
        return storage.get(userId);
    }

    public boolean existsByUserId(String userId) {
        return storage.containsKey(userId);
    }
}
