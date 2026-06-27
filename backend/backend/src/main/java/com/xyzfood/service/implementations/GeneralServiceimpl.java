package com.xyzfood.service.implementations;
import com.xyzfood.dto.response.TableResponse;
import com.xyzfood.entities.Restaurant_table;
import com.xyzfood.entities.Reservation;
import com.xyzfood.entities.User;
import com.xyzfood.entities.FoodOrder;
import com.xyzfood.repositories.TableRepository;
import com.xyzfood.repositories.FoodOrderRepository;
import com.xyzfood.repositories.ReservationRepository;
import com.xyzfood.repositories.UserRepository;
import com.xyzfood.dto.response.FoodResponse;
import com.xyzfood.dto.response.FoodOrderResponse;
import com.xyzfood.dto.response.ReservationResponse;
import com.xyzfood.dto.request.ReservationStatusRequest;
import com.xyzfood.repositories.FoodRepository;
import com.xyzfood.service.interfaces.GeneralService;
import java.util.List;
import org.springframework.stereotype.Service;
import com.xyzfood.dto.response.APIResponse;
import java.util.ArrayList;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GeneralServiceimpl implements GeneralService {
    private final TableRepository tableRepository;
    private final FoodRepository foodRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final FoodOrderRepository foodOrderRepository;
    public GeneralServiceimpl(TableRepository tableRepository, FoodRepository foodRepository, ReservationRepository reservationRepository, 
                                UserRepository userRepository, FoodOrderRepository foodOrderRepository) {
        this.tableRepository = tableRepository;
        this.foodRepository = foodRepository;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.foodOrderRepository = foodOrderRepository;
    }
    @Override
    public List<TableResponse> getAllTables() { 
        return tableRepository.findAll().stream()
                .map(table -> new TableResponse(table.getNumber(), table.getSeats(), table.getFloor(), table.getDelete()))
                .toList();
    }
    @Override
    public List<FoodResponse> getAllFoods() {
        return foodRepository.findAll().stream()
                .map(food -> new FoodResponse(food.getName(), food.getDescription(), food.getPrice(), food.getImage_path(), food.getCategory(), food.getDelete()))
                .toList();
    }
    @Override
    public TableResponse getTable(int request) {
        Restaurant_table table = tableRepository.findByNumber(request);
            return new TableResponse(table.getNumber(),table.getSeats(),table.getFloor(),table.getDelete());
    }
    @Override
    public boolean checkReservationCode(String request) {
        Reservation reservation = reservationRepository.findByreservationCode(request);
        if(reservation == null){
            return false;
        }
        else{
            return true;
        }
    }
    @Override
    public List<ReservationResponse> reservationforCustomerId(String request){
            User user = userRepository.findBycustomerId(request);
            return reservationRepository.findByuser(user).stream()
                .map(reservation -> new ReservationResponse(reservation.getReservationCode(), user.getCustomerId(), 
                    user.getFullName(), reservation.getTable().getNumber(), reservation.getReservationTime(), reservation.getCreatedAt(), reservation.getguestCount(),
                    reservation.getStatus(), reservation.getNotes()))
                    .toList();
    }
    @Override
    public List<FoodOrderResponse> foodOrderforreservationCode(String request){
        Reservation reservation = reservationRepository.findByreservationCode(request);
        if(reservation == null){
            return new ArrayList<>();
        }
        else{
            List<FoodOrder> foodOrders = foodOrderRepository.findByreservation(reservation);
            return foodOrders.stream()
                .map(foodOrder -> new FoodOrderResponse(foodOrder.getfood().getName(),foodOrder.getquantity(),foodOrder.getsubtotal()))
                .toList();
        }
    }
    @Override
    @Transactional
    public APIResponse updateReservationStatus(ReservationStatusRequest request){
        Reservation reservation = reservationRepository.findByreservationCode(request.getReservationCode());
        if(reservation == null){
            return new APIResponse(false,"Không tìm thấy đơn");
        }
        else{
            reservation.setStatus(request.getStatus());
            reservationRepository.save(reservation);
            return new APIResponse(true,"Cập nhật trạng thái thành công"); 
        }
    }
    @Override
    public List<FoodResponse> getFoodsByCategory(String request) {
        return foodRepository.findByCategory(request).stream()
                .map(food -> new FoodResponse(food.getName(), food.getDescription(), food.getPrice(), food.getImage_path(), food.getCategory(), food.getDelete()))
                .toList();
    }
}
