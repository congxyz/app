package com.xyzfood.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.xyzfood.entities.Restaurant_table;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepository extends JpaRepository<Restaurant_table, Long> {
     Restaurant_table findByNumber(int number);
}
