package com.xyzfood.dto.request;

public class ReservationStatusRequest {
    private String reservationCode;
    private String status;
    public  ReservationStatusRequest() {

    }
    public String getReservationCode(){
        return reservationCode;
    }
    public String getStatus(){
        return status;
    }
}
