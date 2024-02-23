package com.kine.Kine.food.service.service;
import com.kine.Kine.food.service.repository.FoodRepository;
import com.kine.Kine.food.service.repository.Food;
import org.springframework.stereotype.Service;
import com.kine.Kine.food.service.dto.FoodDTO;
import org.springframework.data.domain.Example;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
@Service
public class FoodService {
    private final FoodRepository foodRepository;
    @Autowired
    public FoodService(FoodRepository foodRepository){
        this.foodRepository = foodRepository;
    }
    public Iterable<FoodDTO> getFood() {
        Iterable<Food> foundFood = foodRepository.findAll();
        List<FoodDTO> foodList = new ArrayList<>();
        for (Food food : foundFood)
            foodList.add(new FoodDTO(food.getId(), food.getName(), food.getCalories(), food.getProteins(), food.getCarbs(), food.getFat()));
        return foodList;
    }

    public Iterable<FoodDTO> findFoodByName(String name){
        Iterable<Food> foundFood = foodRepository.findFoodByName(name);
        List<FoodDTO> foodList = new ArrayList<>();
        for (Food food : foundFood)
            foodList.add(new FoodDTO(food.getId(), food.getName(), food.getCalories(), food.getProteins(), food.getCarbs(), food.getFat()));
        return foodList;
    }
    public FoodDTO createFood(FoodDTO foodDTO){
        Food food = foodRepository.save(new Food(foodDTO.getName(), foodDTO.getCalories(), foodDTO.getProteins(), foodDTO.getCarbs(), foodDTO.getFat()));
        return new FoodDTO(food.getId(), food.getName(), food.getCalories(), food.getProteins(), food.getCarbs(), food.getFat());
    }


}
