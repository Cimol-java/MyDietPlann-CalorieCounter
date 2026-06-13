package com.mydietplan.menu;

import java.util.List;

import com.mydietplan.exception.AppException;
import com.mydietplan.model.Food;
import com.mydietplan.service.FoodCatalogService;
import com.mydietplan.util.ConsoleUtil;
import com.mydietplan.util.InputValidator;

public class FoodCatalogMenu {
    private final FoodCatalogService catalogService;

    public FoodCatalogMenu(FoodCatalogService catalogService) {
        this.catalogService = catalogService;
    }

    // Helper aman untuk memotong ID agar tidak StringIndexOutOfBoundsException
    private String shortId(String id) {
        return (id != null && id.length() >= 8) ? id.substring(0, 8) : id;
    }

    public void show() {
        while (true) {
            ConsoleUtil.clearScreen();
            ConsoleUtil.printHeader("Menu Pengelolaan Katalog Makanan");
            System.out.println("1. Lihat Semua Menu");
            System.out.println("2. Tambah Menu Baru");
            System.out.println("3. Cari Menu");
            System.out.println("4. Edit Menu");
            System.out.println("5. Hapus Menu");
            System.out.println("0. Kembali");
            ConsoleUtil.printSeparator();

            int choice = InputValidator.readMenu("Pilih: ", 0, 5);
            switch (choice) {
                case 1 -> showAllFoods();
                case 2 -> addFood();
                case 3 -> searchFood();
                case 4 -> editFood();
                case 5 -> deleteFood();
                case 0 -> {
                    return;
                }
            }
        }
    }

    private void showAllFoods() {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("Daftar Menu Makanan Global");
        try {
            List<Food> foods = catalogService.getAllFoods();
            if (foods.isEmpty()) {
                System.out.println("Katalog makanan kosong.");
            } else {
                for (int i = 0; i < foods.size(); i++) {
                    Food f = foods.get(i);
                    System.out.printf("%d. %s (%.1f kkal, %.1f g protein) [ID: %s]\n",
                            i + 1, f.getName(), f.getCalories(), f.getProtein(), shortId(f.getId()));
                }
            }
        } catch (AppException e) {
            System.out.println("[ERROR] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR] Terjadi kesalahan tidak terduga. Silakan coba lagi.");
        }
        ConsoleUtil.pressEnterToContinue();
    }

    private void addFood() {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("Tambah Menu Baru");
        try {
            String name = InputValidator.readNonEmpty("Nama makanan   : ");
            double calories = InputValidator.readDouble("Jumlah Kalori  : ");
            double protein = InputValidator.readDouble("Jumlah Protein (g) : ");

            catalogService.addFood(name, calories, protein);
            System.out.println("[OK] Makanan berhasil ditambahkan ke katalog!");
        } catch (AppException e) {
            System.out.println("[ERROR] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR] Terjadi kesalahan tidak terduga. Silakan coba lagi.");
        }
        ConsoleUtil.pressEnterToContinue();
    }

    private void searchFood() {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("Cari Menu Makanan");
        try {
            String query = InputValidator.readNonEmpty("Kata kunci nama: ");
            List<Food> results = catalogService.searchFood(query);
            if (results.isEmpty()) {
                System.out.println("Tidak ditemukan makanan dengan nama tersebut.");
            } else {
                System.out.println("Hasil Pencarian:");
                for (int i = 0; i < results.size(); i++) {
                    Food f = results.get(i);
                    System.out.printf("%d. %s (%.1f kkal, %.1f g protein) [ID: %s]\n",
                            i + 1, f.getName(), f.getCalories(), f.getProtein(), shortId(f.getId()));
                }
            }
        } catch (AppException e) {
            System.out.println("[ERROR] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR] Terjadi kesalahan tidak terduga. Silakan coba lagi.");
        }
        ConsoleUtil.pressEnterToContinue();
    }

    private void editFood() {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("Edit Menu Makanan");
        try {
            List<Food> foods = catalogService.getAllFoods();
            if (foods.isEmpty()) {
                System.out.println("Katalog kosong, tidak ada yang dapat diedit.");
                ConsoleUtil.pressEnterToContinue();
                return;
            }

            for (int i = 0; i < foods.size(); i++) {
                Food f = foods.get(i);
                System.out.printf("%d. %s (%.1f kkal, %.1f g protein)\n",
                        i + 1, f.getName(), f.getCalories(), f.getProtein());
            }

            int idx = InputValidator.readMenu("Pilih nomor menu yang akan diedit: ", 1, foods.size()) - 1;
            Food target = foods.get(idx);

            String newName = InputValidator.readNonEmpty("Nama baru [" + target.getName() + "]: ");
            double newCal = InputValidator.readDouble("Kalori baru [" + target.getCalories() + "]: ");
            double newProt = InputValidator.readDouble("Protein baru [" + target.getProtein() + "]: ");

            boolean success = catalogService.editFood(target.getId(), newName, newCal, newProt);
            if (success) {
                System.out.println("[OK] Menu makanan berhasil diperbarui.");
            } else {
                System.out.println("[ERROR] Gagal memperbarui menu makanan.");
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

    private void deleteFood() {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("Hapus Menu Makanan");
        try {
            List<Food> foods = catalogService.getAllFoods();
            if (foods.isEmpty()) {
                System.out.println("Katalog kosong, tidak ada yang dapat dihapus.");
                ConsoleUtil.pressEnterToContinue();
                return;
            }

            for (int i = 0; i < foods.size(); i++) {
                Food f = foods.get(i);
                System.out.printf("%d. %s [ID: %s]\n", i + 1, f.getName(), shortId(f.getId()));
            }

            int idx = InputValidator.readMenu("Pilih nomor menu yang akan dihapus: ", 1, foods.size()) - 1;
            Food target = foods.get(idx);

            boolean success = catalogService.deleteFood(target.getId());
            if (success) {
                System.out.println("[OK] Menu makanan berhasil dihapus dari katalog.");
            } else {
                System.out.println("[ERROR] Gagal menghapus menu makanan.");
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
