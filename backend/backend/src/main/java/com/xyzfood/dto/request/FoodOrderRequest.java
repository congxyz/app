package com.xyzfood.dto.request;

public class FoodOrderRequest {
    private String reservationCode;
    private String foodName;
    private int quantity;
    
    public FoodOrderRequest(String reservationCode, String foodName, int quantity){
        this.reservationCode = reservationCode;
        this.foodName = foodName;
        this.quantity = quantity;
    }

    public String getreservationCode(){
        return reservationCode;
    } 
    public String getfoodName(){
        return foodName;
    } 
    public int getfoodQuantity(){
        return quantity;
    } 
}
