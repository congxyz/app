package com.xyzfood.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.xyzfood.entities.Food;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByCategory(String category);
    Food findByname(String name);
}
