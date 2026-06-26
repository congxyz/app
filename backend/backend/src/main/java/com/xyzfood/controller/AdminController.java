package com.xyzfood.controller;

import javax.annotation.processing.Generated;
import com.xyzfood.dto.response.APIResponse;
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
import org.springframework.security.access.prepost.PreAuthorize;
import com.xyzfood.dto.request.TableRequest;

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
    @PostMapping("/tables/save")
    public APIResponse saveTable(@RequestBody TableRequest request) {
        return adminService.saveTable(request);
    }
    
}
