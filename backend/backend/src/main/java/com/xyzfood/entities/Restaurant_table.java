package com.xyzfood.entities;

import javax.annotation.processing.SupportedSourceVersion;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "restaurant_tables")
public class Restaurant_table {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private int number;
    @Column(nullable = false)
    private int seats;
    @Column(nullable = false)
    private int floor;

    public Restaurant_table() {
    }
    public Long getId() {
        return id;
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
    public void setId(Long id) {
        this.id = id;
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
