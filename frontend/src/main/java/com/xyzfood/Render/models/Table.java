package com.xyzfood.Render.models;

public class Table {
    private int number;
    private int seats;
    private int floor;
    private boolean reserved;

    public Table(){

    }
    public Table(int number, int seats, int floor, boolean reserved) {
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

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }
    public int getFloor() {
        return floor;
    }
    public void setFloor(int floor) {
        this.floor = floor;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getStatusText() {
        return reserved ? "ĐÃ ĐẶT" : "TRỐNG";
    }
}
