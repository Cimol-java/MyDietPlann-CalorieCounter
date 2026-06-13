package com.mydietplan.repository;

import com.mydietplan.model.User;
import java.util.HashMap;

public class UserRepository {
    private final HashMap<String, User> storage = new HashMap<>();

    public void save(User user) {
        storage.put(user.getEmail(), user);
    }

    public User findByEmail(String email) {
        return storage.get(email);
    }

    public boolean existsByEmail(String email) {
        return storage.containsKey(email);
    }
}
