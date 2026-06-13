package com.mydietplan.util;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleUtil {

    public static void clearScreen() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls")
                    .inheritIO()
                    .start()
                    .waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException e) {
            System.out.println("[WARN] Tidak dapat membersihkan layar: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("[WARN] Proses clear screen terganggu.");
        }
    }

    public static void printSeparator() {
        System.out.println("==".repeat(25));
    }

    public static void printHeader(String title) {
        printSeparator();
        System.out.println("  " + title.toUpperCase());
        printSeparator();
    }

    public static void pressEnterToContinue() {
        System.out.print("\nTekan Enter untuk melanjutkan...");
        try {
            System.in.read();
            // Buang sisa newline di buffer
            new Scanner(System.in).nextLine();
        } catch (IOException e) {
            // abaikan
        }
    }
}
