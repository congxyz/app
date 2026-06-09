package com.xyzfood.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.xyzfood.entities.User;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.xyzfood.entities.User.Role;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findBycustomerId(String customerId);
    List<User> findAllByRole(User.Role role);
} 
