package com.mydietplan.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputValidator {
    private static final Scanner scanner = new Scanner(System.in);

    public static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("[ERROR] Input tidak boleh kosong.");
                    continue;
                }
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Input tidak valid. Masukkan angka bulat (contoh: 25).");
            } catch (Exception e) {
                System.out.println("[ERROR] Terjadi kesalahan: " + e.getMessage());
            }
        }
    }

    public static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("[ERROR] Input tidak boleh kosong.");
                    continue;
                }
                double value = Double.parseDouble(input);
                if (value <= 0) {
                    System.out.println("[ERROR] Nilai harus lebih besar dari 0.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Input tidak valid. Masukkan angka desimal (contoh: 65.5).");
            } catch (Exception e) {
                System.out.println("[ERROR] Terjadi kesalahan: " + e.getMessage());
            }
        }
    }

    public static String readNonEmpty(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (!input.isEmpty()) return input;
                System.out.println("[ERROR] Input tidak boleh kosong.");
            } catch (Exception e) {
                System.out.println("[ERROR] Gagal membaca input: " + e.getMessage());
            }
        }
    }

    public static int readMenu(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("[ERROR] Input tidak boleh kosong.");
                    continue;
                }
                int choice = Integer.parseInt(input);
                if (choice >= min && choice <= max) return choice;
                System.out.println("[ERROR] Pilihan tidak valid. Masukkan angka antara " + min + " dan " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Masukkan angka, bukan teks.");
            } catch (Exception e) {
                System.out.println("[ERROR] Terjadi kesalahan: " + e.getMessage());
            }
        }
    }

    public static LocalDate readDate(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        while (true) {
            try {
                System.out.print(prompt + " (format: dd-MM-yyyy): ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("[ERROR] Input tidak boleh kosong.");
                    continue;
                }
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("[ERROR] Format tanggal salah. Gunakan format dd-MM-yyyy (contoh: 13-06-2025).");
            } catch (Exception e) {
                System.out.println("[ERROR] Terjadi kesalahan: " + e.getMessage());
            }
        }
    }
}
