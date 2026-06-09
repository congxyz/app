package com.xyzfood.Render.services;

import java.io.File;
import java.util.List;
import com.xyzfood.Render.dto.Response.APIResponse;
import com.xyzfood.Render.dto.Response.FoodImageUploadResponse;
import com.xyzfood.Render.models.Food;
import com.xyzfood.Render.models.FoodOrder;

public class FoodService {
    private final ApiService apiService = ApiService.getInstance();

    public List<Food> getFoods() {
        return apiService.foods();
    }
    public APIResponse createFoodOrder(String reservationCode , String foodName, int quantity ){
        return apiService.createFoodOrder(reservationCode,foodName,quantity);
    }
    public List<FoodOrder> foodOrderforreservationCode(String reservationCode){
        return apiService.foodOrderforreservationCode(reservationCode);
    }
    public FoodImageUploadResponse uploadFoodImage(File imageFile) {
        return apiService.uploadFoodImage(imageFile);
    }
    public APIResponse saveFood(Food food) {
        return apiService.saveFood(food);
    }
    public APIResponse deleteFood(String foodName) {
        return apiService.deleteFood(foodName);
    }
    public List<Food> getFoodsByCategory(String category) {
        return apiService.getFoodsByCategory(category);
    }
}
