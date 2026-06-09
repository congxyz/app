package com.xyzfood.Render.dto.Request;

public class FoodOrderRequest {
    private String reservationCode;
    private String foodName;
    private int quantity;

    public FoodOrderRequest(String reservationCode, String foodName, int quantity){
        this.reservationCode = reservationCode;
        this.foodName = foodName;
        this.quantity = quantity;
    }
    public String getReservationCode(){
        return reservationCode;
    }
    public String getFoodName(){
        return foodName;
    }
    public int getQuantity(){
        return quantity;
    }
}
