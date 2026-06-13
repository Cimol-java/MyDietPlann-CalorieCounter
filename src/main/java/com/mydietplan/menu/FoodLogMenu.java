package com.mydietplan.menu;

import java.util.List;

import com.mydietplan.exception.AppException;
import com.mydietplan.model.Food;
import com.mydietplan.model.FoodLog;
import com.mydietplan.service.FoodCatalogService;
import com.mydietplan.service.FoodService;
import com.mydietplan.util.ConsoleUtil;
import com.mydietplan.util.DateUtil;
import com.mydietplan.util.InputValidator;

public class FoodLogMenu {
    private final FoodService foodService;
    private final FoodCatalogService catalogService;

    public FoodLogMenu(FoodService foodService, FoodCatalogService catalogService) {
        this.foodService = foodService;
        this.catalogService = catalogService;
    }

    // Helper aman untuk memotong ID
    private String shortId(String id) {
        return (id != null && id.length() >= 8) ? id.substring(0, 8) : id;
    }

    public void show(String userId) {
        while (true) {
            ConsoleUtil.clearScreen();
            ConsoleUtil.printHeader("Menu Catatan Makanan Harian");
            System.out.println("1. Catat dari Katalog");
            System.out.println("2. Catat Manual");
            System.out.println("3. Lihat Hari Ini");
            System.out.println("4. Cari");
            System.out.println("5. Edit");
            System.out.println("6. Hapus");
            System.out.println("0. Kembali");
            ConsoleUtil.printSeparator();

            int choice = InputValidator.readMenu("Pilih: ", 0, 6);
            switch (choice) {
                case 1 -> logFromCatalog(userId);
                case 2 -> logManual(userId);
                case 3 -> viewToday(userId);
                case 4 -> searchLogs(userId);
                case 5 -> editLog(userId);
                case 6 -> deleteLog(userId);
                case 0 -> {
                    return;
                }
            }
        }
    }

    private void logFromCatalog(String userId) {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("Catat Makanan dari Katalog");
        try {
            List<Food> foods = catalogService.getAllFoods();
            if (foods.isEmpty()) {
                System.out.println("Katalog makanan kosong. Tambahkan menu baru lewat 'Food Menu' terlebih dahulu.");
                ConsoleUtil.pressEnterToContinue();
                return;
            }

            for (int i = 0; i < foods.size(); i++) {
                Food f = foods.get(i);
                System.out.printf("%d. %s (%.1f kkal, %.1f g protein)\n",
                        i + 1, f.getName(), f.getCalories(), f.getProtein());
            }

            int idx = InputValidator.readMenu("Pilih nomor menu: ", 1, foods.size()) - 1;
            Food target = foods.get(idx);

            foodService.logFood(userId, target.getName(), target.getCalories(), target.getProtein());
            System.out.println("[OK] Berhasil mencatat makanan: " + target.getName());
        } catch (AppException e) {
            System.out.println("[ERROR] " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("[ERROR] Pilihan tidak valid.");
        } catch (Exception e) {
            System.out.println("[ERROR] Terjadi kesalahan tidak terduga. Silakan coba lagi.");
        }
        ConsoleUtil.pressEnterToContinue();
    }

    private void logManual(String userId) {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("Catat Makanan secara Manual");
        try {
            String name = InputValidator.readNonEmpty("Nama makanan   : ");
            double calories = InputValidator.readDouble("Jumlah Kalori  : ");
            double protein = InputValidator.readDouble("Jumlah Protein (g) : ");

            foodService.logFood(userId, name, calories, protein);
            System.out.println("[OK] Berhasil mencatat makanan.");
        } catch (AppException e) {
            System.out.println("[ERROR] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR] Terjadi kesalahan tidak terduga. Silakan coba lagi.");
        }
        ConsoleUtil.pressEnterToContinue();
    }

    private void viewToday(String userId) {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("Makanan Dikonsumsi Hari Ini");
        try {
            List<FoodLog> todayLogs = foodService.getTodayFoods(userId);
            if (todayLogs.isEmpty()) {
                System.out.println("Belum ada makanan yang dicatat hari ini.");
            } else {
                double totalCal = 0;
                double totalProt = 0;
                for (int i = 0; i < todayLogs.size(); i++) {
                    FoodLog log = todayLogs.get(i);
                    System.out.printf("%d. %s (%.1f kkal, %.1f g protein) [ID: %s] [%s]\n",
                            i + 1, log.getFoodName(), log.getCalories(), log.getProtein(),
                            shortId(log.getId()), DateUtil.formatDateTime(log.getLoggedAt()));
                    totalCal += log.getCalories();
                    totalProt += log.getProtein();
                }
                ConsoleUtil.printSeparator();
                System.out.printf("Total Kalori  : %.1f kkal\n", totalCal);
                System.out.printf("Total Protein : %.1f g\n", totalProt);
            }
        } catch (AppException e) {
            System.out.println("[ERROR] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR] Terjadi kesalahan tidak terduga. Silakan coba lagi.");
        }
        ConsoleUtil.pressEnterToContinue();
    }

    private void searchLogs(String userId) {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("Cari Catatan Makanan");
        try {
            String query = InputValidator.readNonEmpty("Kata kunci nama: ");
            List<FoodLog> results = foodService.searchFoodLog(userId, query);
            if (results.isEmpty()) {
                System.out.println("Tidak ditemukan catatan dengan kata kunci tersebut.");
            } else {
                System.out.println("Hasil Pencarian:");
                for (int i = 0; i < results.size(); i++) {
                    FoodLog log = results.get(i);
                    System.out.printf("%d. %s (%.1f kkal, %.1f g protein) [%s]\n",
                            i + 1, log.getFoodName(), log.getCalories(), log.getProtein(),
                            DateUtil.formatDateTime(log.getLoggedAt()));
                }
            }
        } catch (AppException e) {
            System.out.println("[ERROR] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR] Terjadi kesalahan tidak terduga. Silakan coba lagi.");
        }
        ConsoleUtil.pressEnterToContinue();
    }

    private void editLog(String userId) {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("Ubah Catatan Makanan");
        try {
            List<FoodLog> todayLogs = foodService.getTodayFoods(userId);
            if (todayLogs.isEmpty()) {
                System.out.println("Tidak ada catatan makanan hari ini yang dapat diedit.");
                ConsoleUtil.pressEnterToContinue();
                return;
            }

            for (int i = 0; i < todayLogs.size(); i++) {
                FoodLog log = todayLogs.get(i);
                System.out.printf("%d. %s (%.1f kkal, %.1f g protein)\n",
                        i + 1, log.getFoodName(), log.getCalories(), log.getProtein());
            }

            int idx = InputValidator.readMenu("Pilih nomor catatan: ", 1, todayLogs.size()) - 1;
            FoodLog target = todayLogs.get(idx);

            String newName = InputValidator.readNonEmpty("Nama makanan baru [" + target.getFoodName() + "]: ");
            double newCal = InputValidator.readDouble("Kalori baru [" + target.getCalories() + "]: ");
            double newProt = InputValidator.readDouble("Protein baru [" + target.getProtein() + "]: ");

            boolean success = foodService.editFoodLog(userId, target.getId(), newName, newCal, newProt);
            if (success) {
                System.out.println("[OK] Catatan makanan berhasil diperbarui.");
            } else {
                System.out.println("[ERROR] Gagal memperbarui catatan makanan.");
            }
        } catch (AppException e) {
            System.out.println("[ERROR] " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("[ERROR] Pilihan tidak valid.");
        } catch (Exception e) {
            System.out.println("[ERROR] Terjadi kesalahan tidak terduga. Silakan coba lagi.");
        }
        ConsoleUtil.pressEnterToContinue();
    }

    private void deleteLog(String userId) {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("Hapus Catatan Makanan");
        try {
            List<FoodLog> todayLogs = foodService.getTodayFoods(userId);
            if (todayLogs.isEmpty()) {
                System.out.println("Tidak ada catatan makanan hari ini yang dapat dihapus.");
                ConsoleUtil.pressEnterToContinue();
                return;
            }

            for (int i = 0; i < todayLogs.size(); i++) {
                FoodLog log = todayLogs.get(i);
                System.out.printf("%d. %s [ID: %s]\n", i + 1, log.getFoodName(), shortId(log.getId()));
            }

            int idx = InputValidator.readMenu("Pilih nomor catatan: ", 1, todayLogs.size()) - 1;
            FoodLog target = todayLogs.get(idx);

            boolean success = foodService.deleteFoodLog(userId, target.getId());
            if (success) {
                System.out.println("[OK] Catatan makanan berhasil dihapus.");
            } else {
                System.out.println("[ERROR] Gagal menghapus catatan makanan.");
            }
        } catch (AppException e) {
            System.out.println("[ERROR] " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("[ERROR] Pilihan tidak valid.");
        } catch (Exception e) {
            System.out.println("[ERROR] Terjadi kesalahan tidak terduga. Silakan coba lagi.");
        }
        ConsoleUtil.pressEnterToContinue();
    }
}
