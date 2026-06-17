package com.xyzfood.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(
    name = "reservations",
    uniqueConstraints = {
    @UniqueConstraint(
        name = "BookingCheck",
        columnNames = {"table_id","reservationDate"}
    )}
)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "reservation_code", unique = true)
    private String reservationCode;
    @Column(name = "guest_count")
    private int guestCount;
    @Column(name = "reservation_time")
    private LocalDateTime reservationTime;
    @Column(name = "reservationDate")
    private LocalDate reservationDate;
    @Column(name = "status")
    private String status;
    @Column(name = "notes")
    private String notes;
    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "table_id")
    private Restaurant_table table;

    public Reservation() {

    }
    public Reservation( User user, Restaurant_table table, String reservationCode, int guestCount, LocalDateTime reservationTime, LocalDate reservationDate,
                        String status, String notes ) {
            this.user = user;
            this.table =table;
            this.reservationCode = reservationCode;
            this.guestCount = guestCount;
            this.reservationTime = reservationTime;
            this.reservationDate = reservationDate;
            this.status = status;
            this.notes = notes;
    }
    public String getReservationCode() {
        return reservationCode;
    }
    public User getUser() {
        return user;
    }
    public Restaurant_table getTable() {
        return table;
    }
    public int getguestCount() {
        return guestCount;
    }
    public LocalDateTime getReservationTime() {
        return reservationTime;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getNotes() {
        return notes;
    }
}
