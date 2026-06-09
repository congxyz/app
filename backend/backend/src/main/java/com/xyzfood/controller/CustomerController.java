package com.xyzfood.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xyzfood.dto.response.APIResponse;
import com.xyzfood.dto.request.ReservationRequest;
import com.xyzfood.dto.request.FoodOrderRequest;
import com.xyzfood.service.interfaces.CustomerService;

@RestController
@RequestMapping("api/customer")
public class CustomerController {
    private final CustomerService CustomerService;
    public CustomerController(CustomerService CustomerService) {
        this.CustomerService = CustomerService;
    }
    @PostMapping("/createReservation")
    public APIResponse createReservation(@RequestBody ReservationRequest request){
        return CustomerService.createReservation(request);
    }
    @PostMapping("/createFoodOrder")
    public APIResponse createFoodOrder(@RequestBody FoodOrderRequest request){
        return CustomerService.createFoodOrder(request);
    }
}
