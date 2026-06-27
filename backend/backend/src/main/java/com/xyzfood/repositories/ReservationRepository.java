package com.xyzfood.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.xyzfood.entities.Reservation;
import com.xyzfood.entities.User;
import com.xyzfood.entities.Restaurant_table;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByuser(User user);
    Reservation findByreservationCode(String reservationCode);
    List<Reservation> findBytable(Restaurant_table table);
}
