package com.xyzfood.dto.request;

public class TableRequest {
    private int number;
    private int seats;
    private int floor;

    public TableRequest() {

    }
    public TableRequest(int number, int seats, int floor) {
        this.number = number;
        this.floor = floor;
        this.seats = seats;
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
    public void setNumber(int number) {
        this.number = number;
    }
    public void setSeats(int seats) {
        this.seats = seats;
    }
    public void setFloor(int floor) {
        this.floor = floor;
    }
}
