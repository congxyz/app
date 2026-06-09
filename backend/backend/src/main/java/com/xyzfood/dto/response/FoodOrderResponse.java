package com.xyzfood.dto.response;

public class FoodOrderResponse {
    private String foodName;
    private int quantity;
    private float subtotal;
    
    public FoodOrderResponse(String foodName, int quantity, float subtotal) {
        this.foodName = foodName;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }
    public String getFoodName() {
        return foodName;
    }
    public int getQuantity() {
        return quantity;
    }
    public float getSubtotal() {
        return subtotal;
    }
}
