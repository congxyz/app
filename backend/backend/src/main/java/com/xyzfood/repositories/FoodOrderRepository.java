package com.xyzfood.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.xyzfood.entities.FoodOrder;
import com.xyzfood.entities.Reservation;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {
    List<FoodOrder> findByreservation(Reservation reservation);
}
