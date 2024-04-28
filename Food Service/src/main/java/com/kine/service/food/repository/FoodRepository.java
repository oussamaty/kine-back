package com.kine.service.food.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    Page<Food> findFoodByName(String name, Pageable pageable);

    Optional<Food> findFoodById(long id);

    boolean existsByNameAndCaloriesAndProteinsAndCarbsAndFat(String name, float calories, float proteins, float carbs, float fat);
}