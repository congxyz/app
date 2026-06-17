package com.xyzfood.dto.response;


public class TableResponse {
    private int number;
    private int seats;
    private int floor;
    public TableResponse(int number, int seats, int floor) {
        this.number = number;
        this.seats = seats;
        this.floor = floor;
    }
    public int getNumber() {
        return number;
    }
    public int getSeats() {
        return seats;
    }
    public int getFloor() {
        return floor;
    }
    
}
