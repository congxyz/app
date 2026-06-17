package com.xyzfood.Render.models;

public class Table {
    private int number;
    private int seats;
    private int floor;

    public Table(){

    }
    public Table(int number, int seats, int floor) {
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
        return "TRỐNG";
    }
}
