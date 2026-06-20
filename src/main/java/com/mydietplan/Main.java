package com.mydietplan;

import com.mydietplan.auth.menu.AuthMenu;
import com.mydietplan.repository.FoodCatalogRepository;
import com.mydietplan.repository.FoodRepository;
import com.mydietplan.repository.ProfileRepository;
import com.mydietplan.auth.UserRepository;
import com.mydietplan.auth.bizz.AuthBizz;
import com.mydietplan.auth.bizz.IAuthBizz;
import com.mydietplan.repository.WaterRepository;
import com.mydietplan.service.CalculatorService;
import com.mydietplan.service.FoodCatalogService;
import com.mydietplan.service.FoodService;
import com.mydietplan.service.HistoryService;
import com.mydietplan.service.ProfileService;
import com.mydietplan.service.WaterService;
import com.mydietplan.service.impl.CalculatorServiceImpl;
import com.mydietplan.service.impl.FoodCatalogServiceImpl;
import com.mydietplan.service.impl.FoodServiceImpl;
import com.mydietplan.service.impl.HistoryServiceImpl;
import com.mydietplan.service.impl.ProfileServiceImpl;
import com.mydietplan.service.impl.WaterServiceImpl;

public class Main {
    public static void main(String[] args) {
        // Aktifkan ANSI escape code di Windows 10+ (Virtual Terminal Processing)
        enableWindowsAnsiSupport();

        // Inisialisasi repository
        UserRepository userRepository = new UserRepository();
        ProfileRepository profileRepository = new ProfileRepository();
        FoodCatalogRepository foodCatalogRepository = new FoodCatalogRepository();
        FoodRepository foodRepository = new FoodRepository();
        WaterRepository waterRepository = new WaterRepository();

        // Inisialisasi service (polymorphism: tipe = interface, value = implementasi)
        IAuthBizz authBizz = new AuthBizz(userRepository);
        ProfileService profileService = new ProfileServiceImpl(profileRepository);
        FoodCatalogService foodCatalogService = new FoodCatalogServiceImpl(foodCatalogRepository);
        FoodService foodService = new FoodServiceImpl(foodRepository);
        WaterService waterService = new WaterServiceImpl(waterRepository);
        CalculatorService calculatorService = new CalculatorServiceImpl();
        HistoryService historyService = new HistoryServiceImpl(foodRepository, waterRepository);

        // Mulai menu
        AuthMenu authMenu = new AuthMenu(authBizz, profileService, foodService,
                foodCatalogService, waterService, calculatorService, historyService);
        authMenu.show();
    }

    /**
     * Mengaktifkan Virtual Terminal Processing di Windows 10+ agar ANSI escape code
     * bekerja di CMD dan PowerShell tanpa perlu instalasi tambahan.
     * Tidak berpengaruh di Linux/Mac karena ANSI sudah aktif secara default.
     */
    private static void enableWindowsAnsiSupport() {
        String os = System.getProperty("os.name", "").toLowerCase();
        if (!os.contains("win")) {
            return;
        }
        try {
            new ProcessBuilder(
                    "reg", "add",
                    "HKCU\\Console",
                    "/v", "VirtualTerminalLevel",
                    "/t", "REG_DWORD",
                    "/d", "1",
                    "/f").inheritIO().start().waitFor();
        } catch (Exception ignored) {
            // Lanjut tanpa ANSI Windows support jika gagal
        }
    }
}
