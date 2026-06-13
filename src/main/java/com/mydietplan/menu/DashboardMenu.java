package com.mydietplan.menu;

import java.util.List;

import com.mydietplan.exception.AppException;
import com.mydietplan.model.FoodLog;
import com.mydietplan.model.Profile;
import com.mydietplan.model.User;
import com.mydietplan.model.WaterLog;
import com.mydietplan.service.CalculatorService;
import com.mydietplan.service.FoodCatalogService;
import com.mydietplan.service.FoodService;
import com.mydietplan.service.HistoryService;
import com.mydietplan.service.ProfileService;
import com.mydietplan.service.WaterService;
import com.mydietplan.util.ConsoleUtil;
import com.mydietplan.util.InputValidator;

public class DashboardMenu {
    private final User currentUser;
    private final FoodService foodService;
    private final FoodCatalogService catalogService;
    private final WaterService waterService;
    private final ProfileService profileService;
    private final CalculatorService calculatorService;
    private final HistoryService historyService;

    public DashboardMenu(User user, FoodService foodService, FoodCatalogService catalogService, WaterService waterService,
                         ProfileService profileService, CalculatorService calculatorService, HistoryService historyService) {
        this.currentUser = user;
        this.foodService = foodService;
        this.catalogService = catalogService;
        this.waterService = waterService;
        this.profileService = profileService;
        this.calculatorService = calculatorService;
        this.historyService = historyService;
    }

    public void show() {
        while (true) {
            ConsoleUtil.clearScreen();
            ConsoleUtil.printHeader("Dashboard - MyDietPlan");

            try {
                Profile profile = profileService.getProfile(currentUser.getId());
                if (profile == null) {
                    System.out.println("[WARN] Profil belum terisi. Mengalihkan ke menu profil...");
                    ConsoleUtil.pressEnterToContinue();
                    new ProfileMenu(profileService, calculatorService).showCreateProfile(currentUser.getId());
                    continue;
                }

                // Hitung target harian
                double targetCalories = calculatorService.calculateTDEE(profile);
                double targetProtein = calculatorService.calculateProtein(profile);
                double targetWater = calculatorService.calculateWater(profile);

                // Hitung progres harian
                double currentCalories = 0;
                double currentProtein = 0;
                try {
                    List<FoodLog> todayFoods = foodService.getTodayFoods(currentUser.getId());
                    for (FoodLog log : todayFoods) {
                        currentCalories += log.getCalories();
                        currentProtein += log.getProtein();
                    }
                } catch (Exception e) {
                    System.out.println("[WARN] Gagal memuat data makanan hari ini: " + e.getMessage());
                }

                double currentWater = 0;
                try {
                    List<WaterLog> todayWaters = waterService.getTodayWaters(currentUser.getId());
                    for (WaterLog log : todayWaters) {
                        currentWater += log.getAmountMl();
                    }
                } catch (Exception e) {
                    System.out.println("[WARN] Gagal memuat data air hari ini: " + e.getMessage());
                }

                // Tampilkan ringkasan
                System.out.println("Selamat Datang, " + profile.getName() + "!");
                System.out.println("Status Kebutuhan Harian Anda:");
                System.out.printf("  - Energi  : %.1f / %.1f kkal\n", currentCalories, targetCalories);
                System.out.printf("  - Protein : %.1f / %.1f g\n", currentProtein, targetProtein);
                System.out.printf("  - Air     : %.1f / %.1f mL\n", currentWater, targetWater);
                ConsoleUtil.printSeparator();

            } catch (AppException e) {
                System.out.println("[ERROR] " + e.getMessage());
                ConsoleUtil.pressEnterToContinue();
                continue;
            } catch (Exception e) {
                System.out.println("[ERROR] Gagal memuat dashboard. Silakan coba lagi.");
                ConsoleUtil.pressEnterToContinue();
                continue;
            }

            System.out.println("1. Catat Konsumsi Makanan (Food Log)");
            System.out.println("2. Kelola Menu Makanan (Food Menu)");
            System.out.println("3. Catat Konsumsi Air (Water)");
            System.out.println("4. Riwayat (History)");
            System.out.println("5. Profil Saya (Profile)");
            System.out.println("6. Keluar (Logout)");
            ConsoleUtil.printSeparator();

            int choice = InputValidator.readMenu("Pilih menu: ", 1, 6);
            switch (choice) {
                case 1 -> new FoodLogMenu(foodService, catalogService).show(currentUser.getId());
                case 2 -> new FoodCatalogMenu(catalogService).show();
                case 3 -> new WaterMenu(waterService).show(currentUser.getId());
                case 4 -> new HistoryMenu(historyService).show(currentUser.getId());
                case 5 -> new ProfileMenu(profileService, calculatorService).show(currentUser.getId());
                case 6 -> {
                    System.out.println("Berhasil logout.");
                    ConsoleUtil.pressEnterToContinue();
                    return;
                }
            }
        }
    }
}
