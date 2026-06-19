package com.xyzfood.service.implementations;

import com.xyzfood.dto.request.FoodRequest;
import com.xyzfood.dto.response.APIResponse;
import com.xyzfood.dto.response.FoodImageUploadResponse;
import com.xyzfood.dto.response.UserResponse;
import com.xyzfood.dto.response.ReservationResponse;
import com.xyzfood.service.interfaces.AdminService;
import com.xyzfood.repositories.FoodRepository;
import com.xyzfood.repositories.UserRepository;
import com.xyzfood.repositories.ReservationRepository;
import com.xyzfood.repositories.TableRepository;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;
import com.xyzfood.entities.Food;
import com.xyzfood.entities.User;
import com.xyzfood.entities.Reservation;
import com.xyzfood.entities.User.Role;

@Service
public class AdminServiceimpl implements AdminService {
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final TableRepository tableRepository;
    private final FoodRepository foodRepository;
    private final CloudinaryUploadService cloudinaryUploadService;

    public AdminServiceimpl(UserRepository userRepository, ReservationRepository reservationRepository,
                            TableRepository tableRepository, FoodRepository foodRepository,
                            CloudinaryUploadService cloudinaryUploadService) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.tableRepository = tableRepository;
        this.foodRepository = foodRepository;
        this.cloudinaryUploadService = cloudinaryUploadService;
    }

    @Override
    public List<UserResponse> getCustomers() {
        List<User> users = userRepository.findAllByRole(User.Role.CUSTOMER);
        return users.stream().map(user -> new UserResponse(user.getCustomerId(), user.getFullName(), user.getUsername(), user.getRole(), user.getCreatedAt())).toList();
    }

    @Override
    public List<ReservationResponse> getReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream().map(reservation -> new ReservationResponse(reservation.getReservationCode(), 
                                                                                reservation.getUser().getCustomerId(), 
                                                                                reservation.getUser().getFullName(),
                                                                                reservation.getTable().getNumber(), 
                                                                                reservation.getReservationTime(),
                                                                                reservation.getCreatedAt(),
                                                                                reservation.getguestCount(),
                                                                                reservation.getStatus(),
                                                                                reservation.getNotes())).toList();
    }

    @Override
    public FoodImageUploadResponse uploadFoodImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            return new FoodImageUploadResponse(false, "Vui lòng chọn ảnh món ăn", null);
        }

        try {
            String imageUrl = cloudinaryUploadService.uploadFoodImage(image);
            return new FoodImageUploadResponse(true, "Upload ảnh thành công", imageUrl);
        } catch (Exception ex) {
            return new FoodImageUploadResponse(false, "Upload ảnh thất bại: " + ex.getMessage(), null);
        }
    }

    @Override
    @Transactional
    public APIResponse saveFood(FoodRequest request) {
        if (request == null || isBlank(request.getName()) || isBlank(request.getDescription())
                || isBlank(request.getCategory()) || isBlank(request.getImage_path())) {
            return new APIResponse(false, "Vui lòng nhập đầy đủ thông tin món ăn");
        }
        if (request.getPrice() <= 0) {
            return new APIResponse(false, "Giá món ăn phải lớn hơn 0");
        }

        Food food = foodRepository.findByname(request.getName());
        boolean isUpdate = food != null;
        if (food == null) {
            food = new Food();
        }

        food.setName(request.getName());
        food.setDescription(request.getDescription());
        food.setPrice(request.getPrice());
        food.setCategory(request.getCategory());
        food.setImage_path(request.getImage_path());
        food.setDelete(request.getDelete());
        foodRepository.save(food);

        return new APIResponse(true, isUpdate ? "Cập nhật món ăn thành công" : "Thêm món ăn thành công");
    }

    @Override
    @Transactional
    public APIResponse deleteFood(String foodName) {
        if (isBlank(foodName)) {
            return new APIResponse(false, "Tên món ăn không hợp lệ");
        }

        Food food = foodRepository.findByname(foodName.trim());
        if (food == null) {
            return new APIResponse(false, "Không tìm thấy món ăn");
        }
            food.setDelete(true);
            foodRepository.save(food);
            return new APIResponse(true, "Xóa món ăn thành công");
       
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
