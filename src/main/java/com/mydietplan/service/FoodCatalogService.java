package com.mydietplan.service;

import com.mydietplan.model.Food;
import java.util.List;

public interface FoodCatalogService {
    boolean addFood(String name, double calories, double protein);
    boolean editFood(String foodId, String name, double calories, double protein);
    boolean deleteFood(String foodId);
    List<Food> getAllFoods();
    List<Food> searchFood(String keyword);
}
