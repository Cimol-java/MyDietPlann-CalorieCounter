package com.mydietplan.menu;

import com.mydietplan.model.WaterLog;
import com.mydietplan.service.WaterService;
import com.mydietplan.util.ConsoleUtil;
import com.mydietplan.util.DateUtil;
import com.mydietplan.util.InputValidator;
import java.util.List;

public class WaterMenu {
    private final WaterService waterService;

    public WaterMenu(WaterService waterService) {
        this.waterService = waterService;
    }

    public void show(String userId) {
        while (true) {
            ConsoleUtil.clearScreen();
            ConsoleUtil.printHeader("Menu Catatan Air Harian");
            System.out.println("1. Tambah Konsumsi Air");
            System.out.println("2. Lihat Hari Ini");
            System.out.println("3. Edit Catatan");
            System.out.println("4. Hapus Catatan");
            System.out.println("0. Kembali");
            ConsoleUtil.printSeparator();

            int choice = InputValidator.readMenu("Pilih: ", 0, 4);
            switch (choice) {
                case 1 -> handleAddWater(userId);
                case 2 -> handleViewToday(userId);
                case 3 -> handleEditWater(userId);
                case 4 -> handleDeleteWater(userId);
                case 0 -> {
                    return;
                }
            }
        }
    }

    private void handleAddWater(String userId) {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("Catat Konsumsi Air");
        double amount = InputValidator.readDouble("Jumlah Air (mL): ");
        waterService.addWater(userId, amount);
        System.out.println("[OK] Berhasil mencatat konsumsi air.");
        ConsoleUtil.pressEnterToContinue();
    }

    private void handleViewToday(String userId) {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("Konsumsi Air Hari Ini");
        List<WaterLog> logs = waterService.getTodayWaters(userId);
        if (logs.isEmpty()) {
            System.out.println("Belum ada catatan konsumsi air hari ini.");
        } else {
            double total = 0;
            for (int i = 0; i < logs.size(); i++) {
                WaterLog log = logs.get(i);
                System.out.printf("%d. %.1f mL (ID: %s) [%s]\n",
                        i + 1, log.getAmountMl(), log.getId().substring(0, 8),
                        DateUtil.formatDateTime(log.getLoggedAt()));
                total += log.getAmountMl();
            }
            ConsoleUtil.printSeparator();
            System.out.printf("Total Konsumsi Air: %.1f mL\n", total);
        }
        ConsoleUtil.pressEnterToContinue();
    }

    private void handleEditWater(String userId) {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("Ubah Catatan Air");
        List<WaterLog> logs = waterService.getTodayWaters(userId);
        if (logs.isEmpty()) {
            System.out.println("Tidak ada catatan air hari ini untuk diedit.");
            ConsoleUtil.pressEnterToContinue();
            return;
        }

        for (int i = 0; i < logs.size(); i++) {
            WaterLog log = logs.get(i);
            System.out.printf("%d. %.1f mL (ID: %s)\n", i + 1, log.getAmountMl(), log.getId().substring(0, 8));
        }

        int idx = InputValidator.readMenu("Pilih nomor catatan: ", 1, logs.size()) - 1;
        WaterLog target = logs.get(idx);

        double newAmount = InputValidator.readDouble("Jumlah Air Baru (mL): ");
        boolean success = waterService.editWater(userId, target.getId(), newAmount);
        if (success) {
            System.out.println("[OK] Catatan air berhasil diperbarui.");
        } else {
            System.out.println("[ERROR] Gagal memperbarui catatan air.");
        }
        ConsoleUtil.pressEnterToContinue();
    }

    private void handleDeleteWater(String userId) {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("Hapus Catatan Air");
        List<WaterLog> logs = waterService.getTodayWaters(userId);
        if (logs.isEmpty()) {
            System.out.println("Tidak ada catatan air hari ini untuk dihapus.");
            ConsoleUtil.pressEnterToContinue();
            return;
        }

        for (int i = 0; i < logs.size(); i++) {
            WaterLog log = logs.get(i);
            System.out.printf("%d. %.1f mL (ID: %s)\n", i + 1, log.getAmountMl(), log.getId().substring(0, 8));
        }

        int idx = InputValidator.readMenu("Pilih nomor catatan: ", 1, logs.size()) - 1;
        WaterLog target = logs.get(idx);

        boolean success = waterService.deleteWater(userId, target.getId());
        if (success) {
            System.out.println("[OK] Catatan air berhasil dihapus.");
        } else {
            System.out.println("[ERROR] Gagal menghapus catatan air.");
        }
        ConsoleUtil.pressEnterToContinue();
    }
}
