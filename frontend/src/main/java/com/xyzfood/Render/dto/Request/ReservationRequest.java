package com.xyzfood.Render.dto.Request;
import java.time.LocalDateTime;

public class ReservationRequest {
    private String reservationCode;
    private String customerId;
    private int tableNumber;
    private String status;
    private LocalDateTime reservationTime;
    private int guestCount;
    private String notes;
    
    public ReservationRequest(String reservationCode, String customerId, int tableNumber, LocalDateTime reservationTime, int guestCount,String status,String notes) {
        this.reservationCode = reservationCode;
        this.customerId = customerId;
        this.tableNumber = tableNumber;
        this.reservationTime = reservationTime;
        this.guestCount = guestCount;
        this.status = status; 
        this.notes = notes;
    }
    public String getReservationCode() {
        return reservationCode;
    }
    public String getCustomerId() {
        return customerId;
    }
    public int getTableNumber() {
        return tableNumber;
    }
    public LocalDateTime getreservationTime() {
        return reservationTime;
    }
    public int getguestCount() {
        return guestCount;
    }
    public String getStatus() {
        return status;
    }
    public String getNotes() {
        return notes;
    }
}    
