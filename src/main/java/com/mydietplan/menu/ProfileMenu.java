package com.mydietplan.menu;

import com.mydietplan.exception.AppException;
import com.mydietplan.model.ActivityLevel;
import com.mydietplan.model.Gender;
import com.mydietplan.model.Profile;
import com.mydietplan.service.CalculatorService;
import com.mydietplan.service.ProfileService;
import com.mydietplan.util.ConsoleUtil;
import com.mydietplan.util.InputValidator;

public class ProfileMenu {
    private final ProfileService profileService;
    private final CalculatorService calculatorService;

    public ProfileMenu(ProfileService profileService, CalculatorService calculatorService) {
        this.profileService = profileService;
        this.calculatorService = calculatorService;
    }

    public void show(String userId) {
        while (true) {
            ConsoleUtil.clearScreen();
            ConsoleUtil.printHeader("Menu Profil");
            System.out.println("1. Lihat Profil");
            System.out.println("2. Edit Profil");
            System.out.println("0. Kembali");
            ConsoleUtil.printSeparator();

            int choice = InputValidator.readMenu("Pilih: ", 0, 2);
            switch (choice) {
                case 1 -> showProfile(userId);
                case 2 -> showEditProfile(userId);
                case 0 -> {
                    return;
                }
            }
        }
    }

    private void showProfile(String userId) {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("Data Profil Anda");
        try {
            Profile profile = profileService.getProfile(userId);
            if (profile == null) {
                System.out.println("[ERROR] Profil belum dibuat.");
            } else {
                System.out.println("Nama Lengkap       : " + profile.getName());
                System.out.println("Usia               : " + profile.getAge() + " tahun");
                System.out.println("Jenis Kelamin      : " + (profile.getGender() == Gender.MALE ? "Laki-laki" : "Perempuan"));
                System.out.println("Berat Badan        : " + profile.getWeightKg() + " kg");
                System.out.println("Tinggi Badan       : " + profile.getHeightCm() + " cm");
                System.out.println("Tingkat Aktivitas  : " + profile.getActivityLevel().name());
                ConsoleUtil.printSeparator();
                System.out.printf("BMR (Kebutuhan Kalori Dasar)  : %.2f kkal\n", calculatorService.calculateBMR(profile));
                System.out.printf("TDEE (Kebutuhan Kalori Harian): %.2f kkal\n", calculatorService.calculateTDEE(profile));
                System.out.printf("Target Protein Harian         : %.2f g\n", calculatorService.calculateProtein(profile));
                System.out.printf("Target Konsumsi Air Harian    : %.2f mL\n", calculatorService.calculateWater(profile));
            }
        } catch (AppException e) {
            System.out.println("[ERROR] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR] Terjadi kesalahan tidak terduga. Silakan coba lagi.");
        }
        ConsoleUtil.pressEnterToContinue();
    }

    public void showCreateProfile(String userId) {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("Buat Profil Baru");
        try {
            String name = InputValidator.readNonEmpty("Nama lengkap : ");

            int age;
            while (true) {
                age = InputValidator.readInt("Usia (tahun) : ");
                if (age > 0 && age <= 120) break;
                System.out.println("[ERROR] Usia harus antara 1 dan 120.");
            }

            System.out.println("Jenis Kelamin:");
            System.out.println("1. Laki-laki (MALE)");
            System.out.println("2. Perempuan (FEMALE)");
            int genderChoice = InputValidator.readMenu("Pilih: ", 1, 2);
            Gender gender = (genderChoice == 1) ? Gender.MALE : Gender.FEMALE;

            double weight = InputValidator.readDouble("Berat badan (kg) : ");
            double height = InputValidator.readDouble("Tinggi badan (cm): ");

            System.out.println("Tingkat Aktivitas:");
            System.out.println("1. Sangat Ringan (VERY_LIGHT)  - kerja kantoran, jarang olahraga");
            System.out.println("2. Ringan       (LIGHT)         - olahraga 1-3x/minggu");
            System.out.println("3. Sedang       (MODERATE)      - olahraga 3-5x/minggu");
            System.out.println("4. Berat        (HEAVY)         - olahraga intensif 6-7x/minggu");
            System.out.println("5. Sangat Berat (VERY_HEAVY)    - atlet / kerja fisik berat");
            int actChoice = InputValidator.readMenu("Pilih: ", 1, 5);
            ActivityLevel activityLevel = ActivityLevel.values()[actChoice - 1];

            boolean success = profileService.createProfile(userId, name, age, gender, weight, height, activityLevel);
            if (success) {
                System.out.println("[OK] Profil berhasil dibuat!");
            } else {
                System.out.println("[ERROR] Gagal membuat profil.");
            }
        } catch (AppException e) {
            System.out.println("[ERROR] " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("[ERROR] Pilihan tingkat aktivitas tidak valid.");
        } catch (Exception e) {
            System.out.println("[ERROR] Terjadi kesalahan tidak terduga. Silakan coba lagi.");
        }
        ConsoleUtil.pressEnterToContinue();
    }

    private void showEditProfile(String userId) {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("Ubah Profil Anda");
        try {
            Profile profile = profileService.getProfile(userId);
            if (profile == null) {
                System.out.println("[ERROR] Profil belum dibuat. Silakan buat profil terlebih dahulu.");
                ConsoleUtil.pressEnterToContinue();
                return;
            }

            System.out.println("Tekan Enter untuk mempertahankan nilai saat ini.");
            String name = InputValidator.readOptional(
                    "Nama lengkap [" + profile.getName() + "]: ", profile.getName());

            int age;
            while (true) {
                age = InputValidator.readIntOptional(
                        "Usia (tahun) [" + profile.getAge() + "]: ", profile.getAge());
                if (age > 0 && age <= 120) break;
                System.out.println("[ERROR] Usia harus antara 1 dan 120.");
            }

            System.out.println("Jenis Kelamin:");
            System.out.println("1. Laki-laki (MALE)");
            System.out.println("2. Perempuan (FEMALE)");
            int defaultGender = (profile.getGender() == Gender.MALE) ? 1 : 2;
            int genderChoice = InputValidator.readMenuOptional(
                    "Pilih [" + defaultGender + "]: ", 1, 2, defaultGender);
            Gender gender = (genderChoice == 1) ? Gender.MALE : Gender.FEMALE;

            double weight = InputValidator.readDoubleOptional(
                    "Berat badan (kg) [" + profile.getWeightKg() + "]: ", profile.getWeightKg());
            double height = InputValidator.readDoubleOptional(
                    "Tinggi badan (cm) [" + profile.getHeightCm() + "]: ", profile.getHeightCm());

            System.out.println("Tingkat Aktivitas:");
            System.out.println("1. Sangat Ringan (VERY_LIGHT)");
            System.out.println("2. Ringan       (LIGHT)");
            System.out.println("3. Sedang       (MODERATE)");
            System.out.println("4. Berat        (HEAVY)");
            System.out.println("5. Sangat Berat (VERY_HEAVY)");
            int defaultAct = profile.getActivityLevel().ordinal() + 1;
            int actChoice = InputValidator.readMenuOptional(
                    "Pilih [" + defaultAct + "]: ", 1, 5, defaultAct);
            ActivityLevel activityLevel = ActivityLevel.values()[actChoice - 1];

            boolean success = profileService.updateProfile(userId, name, age, gender, weight, height, activityLevel);
            if (success) {
                System.out.println("[OK] Profil berhasil diperbarui!");
            } else {
                System.out.println("[ERROR] Gagal memperbarui profil.");
            }
        } catch (AppException e) {
            System.out.println("[ERROR] " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("[ERROR] Pilihan tingkat aktivitas tidak valid.");
        } catch (Exception e) {
            System.out.println("[ERROR] Terjadi kesalahan tidak terduga. Silakan coba lagi.");
        }
        ConsoleUtil.pressEnterToContinue();
    }
}
