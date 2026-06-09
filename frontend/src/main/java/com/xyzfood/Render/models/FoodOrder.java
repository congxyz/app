package com.xyzfood.Render.models;

public class FoodOrder {
    private String foodName;
    private int quantity;
    private float subtotal;

    public FoodOrder() {

    }
    public FoodOrder(String foodName, int quantity, float subtotal) {
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

    public void increase() {
        quantity++;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public String getDisplayText() {
        return foodName + " x" + quantity + " - " + String.format("%,.0f đ", getSubtotal());
    }
    public void setFoodName(String foodName){
        this.foodName = foodName;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public void setSubtotal(float subtotal){
        this.subtotal = subtotal;
    }
}
