package com.mydietplan.repository;

import com.mydietplan.model.FoodLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FoodRepository {
    private final HashMap<String, ArrayList<FoodLog>> storage = new HashMap<>();

    public void save(String userId, FoodLog log) {
        storage.computeIfAbsent(userId, k -> new ArrayList<>()).add(log);
    }

    public List<FoodLog> findByUserId(String userId) {
        return storage.getOrDefault(userId, new ArrayList<>());
    }

    public FoodLog findById(String userId, String logId) {
        List<FoodLog> logs = storage.get(userId);
        if (logs != null) {
            for (FoodLog log : logs) {
                if (log.getId().equals(logId)) {
                    return log;
                }
            }
        }
        return null;
    }

    public boolean delete(String userId, String logId) {
        List<FoodLog> logs = storage.get(userId);
        if (logs != null) {
            return logs.removeIf(log -> log.getId().equals(logId));
        }
        return false;
    }

    public void update(String userId, FoodLog updated) {
        List<FoodLog> logs = storage.get(userId);
        if (logs != null) {
            for (int i = 0; i < logs.size(); i++) {
                if (logs.get(i).getId().equals(updated.getId())) {
                    logs.set(i, updated);
                    return;
                }
            }
        }
    }
}
