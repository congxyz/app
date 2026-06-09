package com.xyzfood.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.xyzfood.entities.Log;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
     
}
