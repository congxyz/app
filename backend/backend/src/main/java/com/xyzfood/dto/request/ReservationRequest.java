package com.xyzfood.dto.request;

import java.time.LocalDateTime;

public class ReservationRequest {
    private String reservationCode;
    private String customerId;
    private int tableNumber;
    private String status;
    private LocalDateTime reservationTime;
    private int guestCount;
    private String notes;
    public ReservationRequest() {

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
    public void setReservationCode(String reservationCode){
        this.reservationCode = reservationCode;
    }
    public void setCustomerId(String customerId){
        this.customerId = customerId;
    }
    public void setTableNumber(int tableNumber){
        this.tableNumber = tableNumber;
    }
    public void setReservationTime( LocalDateTime reservationTime){
        this.reservationTime = reservationTime;
    }
    public void setStatus( String status){
        this.status = status;
    }
    public void setGuestCount( int guestCount){
        this.guestCount = guestCount;
    }
     public void setNotes( String notes){
        this.notes = notes;
    }
}
