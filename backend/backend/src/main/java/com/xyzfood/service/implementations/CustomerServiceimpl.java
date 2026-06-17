package com.xyzfood.service.implementations;
import com.xyzfood.repositories.TableRepository;
import com.xyzfood.repositories.ReservationRepository;
import com.xyzfood.repositories.UserRepository;
import com.xyzfood.repositories.FoodRepository;
import com.xyzfood.repositories.FoodOrderRepository;
import com.xyzfood.dto.response.APIResponse;
import org.springframework.stereotype.Service;
import com.xyzfood.dto.request.ReservationRequest;
import com.xyzfood.dto.request.FoodOrderRequest;
import com.xyzfood.service.interfaces.CustomerService;
import com.xyzfood.entities.User;
import com.xyzfood.entities.Restaurant_table;
import com.xyzfood.entities.Reservation;
import com.xyzfood.entities.Food;
import com.xyzfood.entities.FoodOrder;
import org.springframework.dao.DataIntegrityViolationException;
import com.xyzfood.dto.request.FoodOrderRequest;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceimpl implements CustomerService{
    private final TableRepository tableRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final FoodRepository foodRepository;
    private final FoodOrderRepository foodOrderRepository;
    public CustomerServiceimpl(TableRepository tableRepository, UserRepository userRepository, ReservationRepository reservationRepository, 
                                FoodRepository foodRepository, FoodOrderRepository foodOrderRepository) {
        this.tableRepository = tableRepository;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.foodRepository = foodRepository;
        this.foodOrderRepository = foodOrderRepository;
    }

    @Override
    @Transactional
    public APIResponse createReservation(ReservationRequest request) {
        User user = userRepository.findBycustomerId(request.getCustomerId());
        Restaurant_table table = null;
        try {
            table = tableRepository.findByNumber(request.getTableNumber());
            if (table == null) {
                return new APIResponse(false,"Bàn không tồn tại");
            }
            Reservation reservation = new Reservation(user,table,request.getReservationCode(),request.getguestCount(),request.getreservationTime(), request.getreservationTime().toLocalDate(),
                                                  request.getStatus(),request.getNotes());
            reservationRepository.save(reservation);
            return new APIResponse(true,"Đặt bàn thành công");
        }
        catch (DataIntegrityViolationException e) {
            return new APIResponse(false,"Bàn này đã có người đặt trong ngày hôm nay, vui lòng chọn bàn khác");
        }
    }

    @Override
    @Transactional
    public APIResponse createFoodOrder(FoodOrderRequest request) {
        Reservation reservation = reservationRepository.findByreservationCode(request.getreservationCode());
        Food food = foodRepository.findByname(request.getfoodName());
        if(reservation!=null){
            FoodOrder foodOrder = new FoodOrder(reservation,food,request.getfoodQuantity(),food.getPrice()*request.getfoodQuantity());
            foodOrderRepository.save(foodOrder);
            return new APIResponse(true,"Order thành công");
        }
        else{
            return new APIResponse(false,"Order thất bại");
        }
        
    }
}
