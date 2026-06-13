package com.mydietplan.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.mydietplan.exception.AppException;
import com.mydietplan.model.FoodLog;
import com.mydietplan.repository.FoodRepository;
import com.mydietplan.service.FoodService;
import com.mydietplan.util.DateUtil;
import com.mydietplan.util.IdGenerator;

public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;

    public FoodServiceImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public void logFood(String userId, String foodName, double calories, double protein) {
        if (foodName == null || foodName.trim().isEmpty()) {
            throw new AppException("Nama makanan tidak boleh kosong.");
        }
        if (calories <= 0) {
            throw new AppException("Kalori harus lebih besar dari 0.");
        }
        if (protein < 0) {
            throw new AppException("Protein tidak boleh bernilai negatif.");
        }
        String id = IdGenerator.generate();
        LocalDateTime now = LocalDateTime.now();
        FoodLog log = new FoodLog(id, userId, now, foodName.trim(), calories, protein);
        foodRepository.save(userId, log);
    }

    @Override
    public boolean editFoodLog(String userId, String logId, String foodName, double calories, double protein) {
        if (foodName == null || foodName.trim().isEmpty()) {
            throw new AppException("Nama makanan tidak boleh kosong.");
        }
        if (calories <= 0) {
            throw new AppException("Kalori harus lebih besar dari 0.");
        }
        if (protein < 0) {
            throw new AppException("Protein tidak boleh bernilai negatif.");
        }
        FoodLog log = foodRepository.findById(userId, logId);
        if (log == null) return false;
        log.setFoodName(foodName.trim());
        log.setCalories(calories);
        log.setProtein(protein);
        foodRepository.update(userId, log);
        return true;
    }

    @Override
    public boolean deleteFoodLog(String userId, String logId) {
        return foodRepository.delete(userId, logId);
    }

    @Override
    public List<FoodLog> getTodayFoods(String userId) {
        LocalDate today = DateUtil.today();
        List<FoodLog> allUserLogs = foodRepository.findByUserId(userId);
        List<FoodLog> todayLogs = new ArrayList<>();
        for (FoodLog log : allUserLogs) {
            if (log.getLoggedAt().toLocalDate().equals(today)) {
                todayLogs.add(log);
            }
        }
        return todayLogs;
    }

    @Override
    public List<FoodLog> searchFoodLog(String userId, String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new AppException("Kata kunci pencarian tidak boleh kosong.");
        }
        List<FoodLog> allUserLogs = foodRepository.findByUserId(userId);
        List<FoodLog> result = new ArrayList<>();
        for (FoodLog log : allUserLogs) {
            if (log.getFoodName().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(log);
            }
        }
        return result;
    }
}
