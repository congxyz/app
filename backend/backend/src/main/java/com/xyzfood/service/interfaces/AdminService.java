package com.xyzfood.service.interfaces;
import com.xyzfood.dto.request.FoodRequest;
import com.xyzfood.dto.request.TableRequest;
import com.xyzfood.dto.response.APIResponse;
import com.xyzfood.dto.response.FoodImageUploadResponse;
import com.xyzfood.dto.response.UserResponse;
import com.xyzfood.dto.response.ReservationResponse;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface AdminService {
    List<UserResponse> getCustomers();
    List<ReservationResponse> getReservations();
    FoodImageUploadResponse uploadFoodImage(MultipartFile image);
    APIResponse saveFood(FoodRequest request);
    APIResponse deleteFood(String foodName);
    APIResponse saveTable(TableRequest request);
}
