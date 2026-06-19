package com.xyzfood.controller;

import com.xyzfood.dto.request.FoodRequest;
import com.xyzfood.dto.response.TableResponse;
import com.xyzfood.dto.response.FoodResponse;
import com.xyzfood.dto.response.FoodOrderResponse;
import com.xyzfood.dto.response.ReservationResponse;
import com.xyzfood.dto.response.FoodImageUploadResponse;
import com.xyzfood.dto.request.ReservationStatusRequest;
import com.xyzfood.service.interfaces.GeneralService;
import com.xyzfood.service.interfaces.AdminService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import com.xyzfood.dto.response.APIResponse;


@RestController
@RequestMapping("api/general")
public class GeneralController {
    private final GeneralService generalService;
    public GeneralController(GeneralService generalService) {
        this.generalService = generalService;
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
    @PostMapping("/foods/upload-image")
    public FoodImageUploadResponse uploadFoodImage(@RequestParam("image") MultipartFile image) {
        return adminService.uploadFoodImage(image);
    }

    @PostMapping("/foods/save")
    public APIResponse saveFood(@RequestBody FoodRequest request) {
        return adminService.saveFood(request);
    }

    @GetMapping("/foods")
    public APIResponse deleteFood(@RequestParam String foodName) {
        return adminService.deleteFood(foodName);
    }
}
