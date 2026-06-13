package com.mydietplan.util;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleUtil {

    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (Exception e) {
            System.out.println();
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
