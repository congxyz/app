package com.xyzfood.Render.models;

public class Table {
    private int number;
    private int seats;
    private int floor;
    private boolean delete;
    public Table(){

    }
    public Table(int number, int seats, int floor, boolean delete) {
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
    public void setFloor(int floor) {
        this.floor = floor;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public void setSeats(int seats) {
        this.seats = seats;
    }
    public void setDelete(boolean delete) {
        this.delete = delete;
    }
}
