package com.xyzfood.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "food_orders")
public class FoodOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "subtotal")
    private float subtotal;
    @Column(name = "created_at")
    @CreationTimestamp
    private Date created_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Food food;
    
    public FoodOrder() {
        
    }

    public FoodOrder (Reservation reservation, Food food, int quantity, float subtotal) {
        this.reservation = reservation;
        this.food = food;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }
    public Food getfood(){
        return food;
    }
    public int getquantity(){
        return quantity;
    }
    public float getsubtotal(){
        return subtotal;
    }
}
