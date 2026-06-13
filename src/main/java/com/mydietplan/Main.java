package com.mydietplan;

import com.mydietplan.menu.MainMenu;
import com.mydietplan.repository.*;
import com.mydietplan.service.*;
import com.mydietplan.service.impl.*;

public class Main {
    public static void main(String[] args) {
        // Inisialisasi repository
        UserRepository userRepository = new UserRepository();
        ProfileRepository profileRepository = new ProfileRepository();
        FoodCatalogRepository foodCatalogRepository = new FoodCatalogRepository();
        FoodRepository foodRepository = new FoodRepository();
        WaterRepository waterRepository = new WaterRepository();

        // Inisialisasi service (polymorphism: tipe = interface, value = implementasi)
        AuthService authService = new AuthServiceImpl(userRepository);
        ProfileService profileService = new ProfileServiceImpl(profileRepository);
        FoodCatalogService foodCatalogService = new FoodCatalogServiceImpl(foodCatalogRepository);
        FoodService foodService = new FoodServiceImpl(foodRepository);
        WaterService waterService = new WaterServiceImpl(waterRepository);
        CalculatorService calculatorService = new CalculatorServiceImpl();
        HistoryService historyService = new HistoryServiceImpl(foodRepository, waterRepository);

        // Mulai menu
        MainMenu mainMenu = new MainMenu(authService, profileService, foodService,
                                          foodCatalogService, waterService, calculatorService, historyService);
        mainMenu.show();
    }
}
