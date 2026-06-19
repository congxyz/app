package com.xyzfood.dto.response;
import java.time.LocalDateTime;

public class ReservationResponse {
    private String reservationCode;
    private String CustomerId;
    private int TableNumber;
    private String status;
    private LocalDateTime reservationTime;
    private int guestCount;
    private String CustomerName;
    private String notes;
    private LocalDateTime createdAt;

    public ReservationResponse(String reservationCode, String CustomerId, String CustomerName, int Tablenumber, LocalDateTime reservationTime, LocalDateTime createdAt,int guestCount,String status,String notes) {
        this.reservationCode = reservationCode;
        this.CustomerId = CustomerId;
        this.CustomerName = CustomerName;
        this.TableNumber = Tablenumber;
        this.reservationTime = reservationTime;
        this.guestCount = guestCount;
        this.status = status; 
        this.notes = notes;
        this.createdAt = createdAt;
    }
    public String getReservationCode() {
        return reservationCode;
    }
    public String getCustomerId() {
        return CustomerId;
    }
    public String getCustomerName() {
        return CustomerName;
    }
    public int getTablenumber() {
        return TableNumber;
    }
    public int getGuestCount() {
        return guestCount;
    }
    public LocalDateTime getReservationTime() {
        return reservationTime;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public String getStatus() {
        return status;
    }
    public String getNotes() {
        return notes;
    }

   

}
