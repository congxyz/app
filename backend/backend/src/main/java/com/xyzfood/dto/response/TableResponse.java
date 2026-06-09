package com.xyzfood.dto.response;


public class TableResponse {
    private int number;
    private int seats;
    private int floor;
    private boolean reserved;
    public TableResponse(int number, int seats, int floor, boolean reserved) {
        this.number = number;
        this.seats = seats;
        this.floor = floor;
        this.reserved = reserved;
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
    public boolean isReserved() {
        return reserved;
    }
    

}
