package com.mydietplan.menu;

import com.mydietplan.service.HistoryService;
import com.mydietplan.util.ConsoleUtil;
import com.mydietplan.util.InputValidator;
import java.time.LocalDate;

public class HistoryMenu {
    private final HistoryService historyService;

    public HistoryMenu(HistoryService historyService) {
        this.historyService = historyService;
    }

    public void show(String userId) {
        while (true) {
            ConsoleUtil.clearScreen();
            ConsoleUtil.printHeader("Menu Riwayat");
            System.out.println("1. Tampilkan Riwayat Berdasarkan Tanggal");
            System.out.println("0. Kembali");
            ConsoleUtil.printSeparator();

            int choice = InputValidator.readMenu("Pilih: ", 0, 1);
            if (choice == 1) {
                LocalDate date = InputValidator.readDate("Masukkan Tanggal");
                ConsoleUtil.clearScreen();
                historyService.showHistoryByDate(userId, date);
                ConsoleUtil.pressEnterToContinue();
            } else {
                return;
            }
        }
    }
}
