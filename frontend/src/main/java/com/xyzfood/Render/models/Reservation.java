package com.xyzfood.Render.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xyzfood.Render.utils.DateUtil;

public class Reservation {
    private String reservationCode;
    @JsonProperty("CustomerId")
    private String CustomerId;
    @JsonProperty("TableNumber")
    private int TableNumber;
    private String status;
    private LocalDateTime reservationTime;
    private int guestCount;
    private String notes;
    @JsonProperty("CustomerName")
    private String CustomerName;
    private LocalDateTime createdAt;
    public Reservation() {

    }

    public Reservation(String reservationCode, String CustomerId, String CustomerName,int TableNumber, LocalDateTime reservationTime, int guestCount,String status,String notes) {
        this.reservationCode = reservationCode;
        this.CustomerId = CustomerId;
        this.CustomerName = CustomerName;
        this.TableNumber = TableNumber;
        this.reservationTime = reservationTime;
        this.guestCount = guestCount;
        this.status = status; 
        this.notes = notes;
    }

    public String getReservationCode() {
        return reservationCode;
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }
    
    public int getGuestCount() {
        return guestCount;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public String getTableNumberText() {
        return "Bàn " + TableNumber;
    }

    public String getTimeText() {
        return DateUtil.formatDateTime(reservationTime);
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public String getNotes(){
        return notes;
    }
    public int getTableNumber() {
        return TableNumber;
    }
    public void setReservationCode(String reservationCode){
        this.reservationCode = reservationCode;
    }
    public void setCustomerId(String CustomerId){
        this.CustomerId = CustomerId;
    }
    public void setCustomerName(String CustomerName){
        this.CustomerName = CustomerName;
    }
    public void setTableNumber(int TableNumber){
        this.TableNumber = TableNumber;
    }
    public void setReservationTime( LocalDateTime reservationTime){
        this.reservationTime = reservationTime;
    }
    public void setcreatedAt( LocalDateTime createdAt){
        this.createdAt = createdAt;
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
