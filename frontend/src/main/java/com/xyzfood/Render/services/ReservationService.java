package com.xyzfood.Render.services;

import java.time.LocalDateTime;
import java.util.List;

import com.xyzfood.Render.models.FoodOrder;
import com.xyzfood.Render.models.Reservation;
import com.xyzfood.Render.models.Table;
import com.xyzfood.Render.models.User;
import com.xyzfood.Render.dto.Response.APIResponse;

public class ReservationService {
    private final ApiService apiService = ApiService.getInstance();

    public APIResponse bookTable(String reservation_code, String CustomerId, int Tablenumber, LocalDateTime reservationTime, int guestCount, String status, String notes)
            {
        return apiService.createReservation(reservation_code, CustomerId, Tablenumber, reservationTime, guestCount, status, notes);
    }

    public List<Reservation> getAllReservations() {
        return apiService.reservations();
    }

    public List<Reservation> getReservationsByCustomer(String customerId) {
        return apiService.reservationsFor(customerId);
    }
    public boolean checkReservationCode(String ReservationCode) {
        return apiService.checkReservationCode(ReservationCode);
    }
    public APIResponse updateReservationStatus(String reservationCode, String status) {
        return apiService.updateReservationStatus(reservationCode, status);
    }
}

