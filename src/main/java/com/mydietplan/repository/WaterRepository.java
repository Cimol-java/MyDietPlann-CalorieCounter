package com.mydietplan.repository;

import com.mydietplan.model.WaterLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WaterRepository {
    private final HashMap<String, ArrayList<WaterLog>> storage = new HashMap<>();

    public void save(String userId, WaterLog log) {
        storage.computeIfAbsent(userId, k -> new ArrayList<>()).add(log);
    }

    public List<WaterLog> findByUserId(String userId) {
        return storage.getOrDefault(userId, new ArrayList<>());
    }

    public WaterLog findById(String userId, String logId) {
        List<WaterLog> logs = storage.get(userId);
        if (logs != null) {
            for (WaterLog log : logs) {
                if (log.getId().equals(logId)) {
                    return log;
                }
            }
        }
        return null;
    }

    public boolean delete(String userId, String logId) {
        List<WaterLog> logs = storage.get(userId);
        if (logs != null) {
            return logs.removeIf(log -> log.getId().equals(logId));
        }
        return false;
    }

    public void update(String userId, WaterLog updated) {
        List<WaterLog> logs = storage.get(userId);
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
