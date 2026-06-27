package com.xyzfood.controller;

import com.xyzfood.dto.response.TableResponse;
import com.xyzfood.dto.response.FoodResponse;
import com.xyzfood.dto.response.FoodOrderResponse;
import com.xyzfood.dto.response.ReservationResponse;
import com.xyzfood.dto.request.ReservationStatusRequest;
import com.xyzfood.service.interfaces.GeneralService;
import com.xyzfood.service.interfaces.AdminService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import com.xyzfood.dto.response.APIResponse;


@RestController
@RequestMapping("api/general")
public class GeneralController {
    private final GeneralService generalService;
    private final AdminService adminService;
    public GeneralController(GeneralService generalService, AdminService adminService) {
        this.generalService = generalService;
        this.adminService = adminService;
    }

    @GetMapping("/tables")
    public List<TableResponse> getAllTables() {
        return generalService.getAllTables();
    }

    @GetMapping("/foods")
    public List<FoodResponse> getAllFoods() {
        return generalService.getAllFoods();
    }
    @GetMapping("/getTable")
    public TableResponse getTable(@RequestParam int request){
        return generalService.getTable(request);
    }
    @GetMapping("/checkReservationCode")
    public boolean checkReservationCode(@RequestParam String request){
        return generalService.checkReservationCode(request);
    }
    @GetMapping("/reservationforCustomorId")
    public List<ReservationResponse> reservationforCustomerId(@RequestParam String request){
        return generalService.reservationforCustomerId(request);
    }
    @GetMapping("/foodOrderforreservationCode")
    public List<FoodOrderResponse> foodOrderforreservationCode(@RequestParam String request){
        return generalService.foodOrderforreservationCode(request);
    }
    @PostMapping("/updateReservationStatus")
    public APIResponse updateReservationStatus(@RequestBody ReservationStatusRequest request){
        return generalService.updateReservationStatus(request);
    }
    @GetMapping("/foodsByCategory")
    public List<FoodResponse> getFoodsByCategory(@RequestParam String request) {
        return generalService.getFoodsByCategory(request);
    }
    
}
