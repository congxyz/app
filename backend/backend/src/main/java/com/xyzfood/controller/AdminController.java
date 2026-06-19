package com.xyzfood.controller;

import javax.annotation.processing.Generated;
import com.xyzfood.dto.request.FoodRequest;
import com.xyzfood.dto.response.APIResponse;
import com.xyzfood.dto.response.FoodImageUploadResponse;
import com.xyzfood.dto.response.UserResponse;
import com.xyzfood.dto.response.ReservationResponse;
import com.xyzfood.service.interfaces.AdminService;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final AdminService adminService;
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    @GetMapping("/users")
    public List<UserResponse> getCustomers() {
        return adminService.getCustomers();
    }
    @GetMapping("/reservations")
    public List<ReservationResponse> getReservations() {
        return adminService.getReservations();
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
