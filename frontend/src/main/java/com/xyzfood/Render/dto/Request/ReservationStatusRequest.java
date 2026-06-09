package com.xyzfood.Render.dto.Request;

public class ReservationStatusRequest {
    private String reservationCode;
    private String status;
    public  ReservationStatusRequest(String reservationCode, String status) {
        this.reservationCode = reservationCode;
        this.status = status;
    }
    public String getReservationCode(){
        return reservationCode;
    }
    public String getStatus(){
        return status;
    }
}
