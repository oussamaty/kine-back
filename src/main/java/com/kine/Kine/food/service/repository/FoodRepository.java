package com.kine.Kine.food.service.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.kine.Kine.food.service.repository.Food;
import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    Iterable<Food> findFoodByName(String name);
    Food findFoodById(long id);
    }