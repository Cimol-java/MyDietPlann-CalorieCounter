package com.mydietplan.service;

import com.mydietplan.model.WaterLog;
import java.util.List;

public interface WaterService {
    void addWater(String userId, double amountMl);
    boolean editWater(String userId, String logId, double amountMl);
    boolean deleteWater(String userId, String logId);
    List<WaterLog> getTodayWaters(String userId);
}
