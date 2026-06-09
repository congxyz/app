package com.xyzfood.service.interfaces;
import com.xyzfood.dto.response.TableResponse;
import java.util.List;
import com.xyzfood.dto.response.FoodResponse;
import com.xyzfood.dto.response.ReservationResponse;
import com.xyzfood.dto.response.APIResponse;
import com.xyzfood.dto.response.FoodOrderResponse;
import com.xyzfood.dto.request.ReservationStatusRequest;


public interface GeneralService {
    List<TableResponse> getAllTables();
    List<FoodResponse> getAllFoods();
    APIResponse ReleaseTable(Integer request);
    TableResponse getTable(int request);
    boolean checkReservationCode(String request);
    List<ReservationResponse> reservationforCustomerId(String request);
    List<FoodOrderResponse> foodOrderforreservationCode(String request);
    APIResponse updateReservationStatus(ReservationStatusRequest request);
    List<FoodResponse> getFoodsByCategory(String request);
}
