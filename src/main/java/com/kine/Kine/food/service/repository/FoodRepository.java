package com.kine.Kine.food.service.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.kine.Kine.food.service.repository.Food;
import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findAll();
    Food findFirstByFood_id(long food_id);
}