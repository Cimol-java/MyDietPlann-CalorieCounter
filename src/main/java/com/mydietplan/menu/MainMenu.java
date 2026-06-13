package com.mydietplan.menu;

import com.mydietplan.model.Profile;
import com.mydietplan.model.User;
import com.mydietplan.service.*;
import com.mydietplan.util.ConsoleUtil;
import com.mydietplan.util.InputValidator;

public class MainMenu {
    private final AuthService authService;
    private final ProfileService profileService;
    private final FoodService foodService;
    private final FoodCatalogService catalogService;
    private final WaterService waterService;
    private final CalculatorService calculatorService;
    private final HistoryService historyService;

    public MainMenu(AuthService authService, ProfileService profileService, FoodService foodService,
                    FoodCatalogService catalogService, WaterService waterService, CalculatorService calculatorService,
                    HistoryService historyService) {
        this.authService = authService;
        this.profileService = profileService;
        this.foodService = foodService;
        this.catalogService = catalogService;
        this.waterService = waterService;
        this.calculatorService = calculatorService;
        this.historyService = historyService;
    }

    public void show() {
        while (true) {
            ConsoleUtil.clearScreen();
            ConsoleUtil.printHeader("MyDietPlan - Calorie Counter");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Keluar");
            ConsoleUtil.printSeparator();

            int choice = InputValidator.readMenu("Pilih: ", 0, 2);
            switch (choice) {
                case 1 -> handleLogin();
                case 2 -> handleRegister();
                case 0 -> {
                    System.out.println("Sampai jumpa!");
                    return;
                }
            }
        }
    }

    private void handleLogin() {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("Login");

        String email = InputValidator.readNonEmpty("Email    : ");
        String password = InputValidator.readNonEmpty("Password : ");

        User user = authService.login(email, password);

        if (user == null) {
            System.out.println("[ERROR] Email atau password salah.");
            ConsoleUtil.pressEnterToContinue();
            return;
        }

        System.out.println("[OK] Login berhasil. Selamat datang, " + user.getEmail() + "!");
        ConsoleUtil.pressEnterToContinue();

        // Check if profile exists
        Profile profile = profileService.getProfile(user.getId());
        if (profile == null) {
            ConsoleUtil.clearScreen();
            ConsoleUtil.printHeader("Lengkapi Profil");
            System.out.println("[INFO] Profil belum diisi. Silakan lengkapi profil terlebih dahulu.");
            System.out.println("[INFO] Profil diperlukan untuk menghitung kebutuhan kalori harian Anda.");
            ConsoleUtil.pressEnterToContinue();

            // Force profile creation
            new ProfileMenu(profileService, calculatorService).showCreateProfile(user.getId());
        }

        // Direct to Dashboard
        new DashboardMenu(user, foodService, catalogService, waterService, profileService, calculatorService, historyService).show();
    }

    private void handleRegister() {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("Daftar Akun Baru (Register)");

        String email = InputValidator.readNonEmpty("Email    : ");
        String password = InputValidator.readNonEmpty("Password : ");

        boolean success = authService.register(email, password);
        if (success) {
            System.out.println("[OK] Register berhasil! Silakan login.");
        } else {
            System.out.println("[ERROR] Register gagal.");
        }
        ConsoleUtil.pressEnterToContinue();
    }
}
