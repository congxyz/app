package com.xyzfood.dto.response;


public class TableResponse {
    private int number;
    private int seats;
    private int floor;
    private boolean delete;
    public TableResponse(int number, int seats, int floor, boolean delete) {
        this.number = number;
        this.seats = seats;
        this.floor = floor;
        this.delete = delete;
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
    public boolean getDelete() {
        return delete;
    }
}
