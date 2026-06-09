package com.xyzfood.service.interfaces;
import com.xyzfood.dto.response.APIResponse;
import com.xyzfood.dto.request.ReservationRequest;
import com.xyzfood.dto.request.FoodOrderRequest;

public interface CustomerService {
    APIResponse createReservation(ReservationRequest request);
    APIResponse createFoodOrder(FoodOrderRequest request);
} 
