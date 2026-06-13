package com.mydietplan.util;

import java.io.IOException;

public class ConsoleUtil {

    // ANSI escape sequence: pindah ke Home + hapus layar + hapus scrollback buffer
    private static final String ANSI_CLEAR = "\033[H\033[2J\033[3J";

    private static final boolean IS_WINDOWS =
            System.getProperty("os.name", "").toLowerCase().contains("win");

    /**
     * Membersihkan layar terminal.
     *
     * Urutan prioritas:
     * 1. ANSI escape code  — bekerja di PowerShell, Windows Terminal, Git Bash, Linux, Mac
     *                        Juga bekerja ketika dijalankan via Maven exec:java
     * 2. ProcessBuilder cls — fallback untuk Windows CMD lama
     * 3. Cetak 50 baris kosong — worst-case fallback
     *
     * Catatan: System.console() TIDAK digunakan sebagai syarat karena akan selalu
     * null ketika program dijalankan via Maven (stdout di-redirect), padahal ANSI
     * tetap bisa bekerja di terminal yang menjalankan Maven.
     */
    public static void clearScreen() {
        if (supportsAnsi()) {
            System.out.print(ANSI_CLEAR);
            System.out.flush();
            return;
        }
        if (IS_WINDOWS) {
            try {
                new ProcessBuilder("cmd", "/c", "cls")
                        .inheritIO()
                        .start()
                        .waitFor();
                return;
            } catch (IOException | InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        }
        // Fallback terakhir: baris kosong
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    /**
     * Mendeteksi apakah terminal mendukung ANSI escape code.
     *
     * Tidak menggunakan System.console() karena akan null saat dijalankan
     * via Maven exec:java meski terminal sesungguhnya mendukung ANSI.
     * Sebagai gantinya, cek environment variable yang di-set oleh terminal.
     */
    private static boolean supportsAnsi() {
        return System.getenv("WT_SESSION") != null      // Windows Terminal
            || System.getenv("ANSICON") != null          // ANSICON
            || System.getenv("TERM") != null             // Unix/Linux/Mac
            || System.getenv("COLORTERM") != null        // terminal dengan warna
            || System.getenv("ConEmuANSI") != null       // ConEmu / cmder
            || "true".equalsIgnoreCase(System.getenv("CLICOLOR_FORCE")); // force flag
    }

    public static void printSeparator() {
        System.out.println("==".repeat(25));
    }

    public static void printHeader(String title) {
        printSeparator();
        System.out.println("  " + title.toUpperCase());
        printSeparator();
    }

    /**
     * Menunggu user menekan Enter sebelum melanjutkan.
     *
     * Membaca langsung dari System.in (tanpa membuat Scanner baru)
     * untuk mencegah bocornya input ke layar berikutnya.
     */
    public static void pressEnterToContinue() {
        System.out.print("\nTekan Enter untuk melanjutkan...");
        System.out.flush();
        try {
            int c;
            do {
                c = System.in.read();
            } while (c != -1 && c != '\n');
        } catch (IOException ignored) {
            // abaikan jika input stream tidak tersedia
        }
    }
}
