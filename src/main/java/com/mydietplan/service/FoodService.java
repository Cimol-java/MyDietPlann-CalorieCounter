package com.mydietplan.service;

import com.mydietplan.model.FoodLog;
import java.util.List;

public interface FoodService {
    void logFood(String userId, String foodName, double calories, double protein);
    boolean editFoodLog(String userId, String logId, String foodName, double calories, double protein);
    boolean deleteFoodLog(String userId, String logId);
    List<FoodLog> getTodayFoods(String userId);
    List<FoodLog> searchFoodLog(String userId, String keyword);
}
