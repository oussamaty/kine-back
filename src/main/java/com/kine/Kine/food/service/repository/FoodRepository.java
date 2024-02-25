package com.kine.Kine.food.service.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    Page<Food> findFoodByName(String name,Pageable pageable );

    Food findFoodById(long id);

    boolean existsByNameAndCaloriesAndProteinsAndCarbsAndFat(String name, double calories, double proteins, double carbs, double fat);
}