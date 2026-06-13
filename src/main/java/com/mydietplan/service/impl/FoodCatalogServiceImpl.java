package com.mydietplan.service.impl;

import com.mydietplan.model.Food;
import com.mydietplan.repository.FoodCatalogRepository;
import com.mydietplan.service.FoodCatalogService;
import com.mydietplan.util.IdGenerator;
import java.util.List;

public class FoodCatalogServiceImpl implements FoodCatalogService {
    private final FoodCatalogRepository catalogRepository;

    public FoodCatalogServiceImpl(FoodCatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    @Override
    public boolean addFood(String name, double calories, double protein) {
        String id = IdGenerator.generate();
        Food food = new Food(id, name, calories, protein);
        catalogRepository.save(food);
        return true;
    }

    @Override
    public boolean editFood(String foodId, String name, double calories, double protein) {
        Food food = catalogRepository.findById(foodId);
        if (food == null) return false;
        food.setName(name);
        food.setCalories(calories);
        food.setProtein(protein);
        catalogRepository.update(food);
        return true;
    }

    @Override
    public boolean deleteFood(String foodId) {
        if (!catalogRepository.existsById(foodId)) return false;
        catalogRepository.delete(foodId);
        return true;
    }

    @Override
    public List<Food> getAllFoods() {
        return catalogRepository.findAll();
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return catalogRepository.searchByName(keyword);
    }
}
