package com.mydietplan.service.impl;

import com.mydietplan.model.FoodLog;
import com.mydietplan.model.WaterLog;
import com.mydietplan.repository.FoodRepository;
import com.mydietplan.repository.WaterRepository;
import com.mydietplan.service.HistoryService;
import com.mydietplan.util.ConsoleUtil;
import com.mydietplan.util.DateUtil;
import java.time.LocalDate;
import java.util.List;

public class HistoryServiceImpl implements HistoryService {
    private final FoodRepository foodRepository;
    private final WaterRepository waterRepository;

    public HistoryServiceImpl(FoodRepository foodRepository, WaterRepository waterRepository) {
        this.foodRepository = foodRepository;
        this.waterRepository = waterRepository;
    }

    @Override
    public void showHistoryByDate(String userId, LocalDate date) {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("Riwayat Tanggal: " + DateUtil.formatDate(date));

        // Get food logs
        List<FoodLog> allFood = foodRepository.findByUserId(userId);
        double totalCalories = 0;
        double totalProtein = 0;
        int foodCount = 0;

        System.out.println("Daftar Makanan:");
        for (FoodLog log : allFood) {
            if (log.getLoggedAt().toLocalDate().equals(date)) {
                foodCount++;
                System.out.printf("- %s: %.1f kkal, %.1f g protein (Dicatat: %s)\n",
                        log.getFoodName(), log.getCalories(), log.getProtein(),
                        DateUtil.formatDateTime(log.getLoggedAt()));
                totalCalories += log.getCalories();
                totalProtein += log.getProtein();
            }
        }
        if (foodCount == 0) {
            System.out.println("  (Tidak ada catatan makanan)");
        }

        System.out.println();
        // Get water logs
        List<WaterLog> allWater = waterRepository.findByUserId(userId);
        double totalWater = 0;
        int waterCount = 0;

        System.out.println("Daftar Konsumsi Air:");
        for (WaterLog log : allWater) {
            if (log.getLoggedAt().toLocalDate().equals(date)) {
                waterCount++;
                System.out.printf("- %.1f mL (Dicatat: %s)\n",
                        log.getAmountMl(), DateUtil.formatDateTime(log.getLoggedAt()));
                totalWater += log.getAmountMl();
            }
        }
        if (waterCount == 0) {
            System.out.println("  (Tidak ada catatan konsumsi air)");
        }

        ConsoleUtil.printSeparator();
        System.out.printf("Total Kalori      : %.1f kkal\n", totalCalories);
        System.out.printf("Total Protein     : %.1f g\n", totalProtein);
        System.out.printf("Total Konsumsi Air: %.1f mL\n", totalWater);
        ConsoleUtil.printSeparator();
    }
}
