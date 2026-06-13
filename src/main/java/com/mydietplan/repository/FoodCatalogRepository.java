package com.mydietplan.repository;

import com.mydietplan.model.Food;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FoodCatalogRepository {
    private final HashMap<String, Food> storage = new HashMap<>();

    public void save(Food food) {
        storage.put(food.getId(), food);
    }

    public Food findById(String id) {
        return storage.get(id);
    }

    public boolean existsById(String id) {
        return storage.containsKey(id);
    }

    public void delete(String id) {
        storage.remove(id);
    }

    public void update(Food updated) {
        storage.put(updated.getId(), updated);
    }

    public List<Food> findAll() {
        return new ArrayList<>(storage.values());
    }

    public List<Food> searchByName(String keyword) {
        List<Food> result = new ArrayList<>();
        for (Food food : storage.values()) {
            if (food.getName().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(food);
            }
        }
        return result;
    }
}
