package com.kine.Kine.food.service.service;
import com.kine.Kine.food.service.dto.UpdateDTO;
import com.kine.Kine.food.service.repository.FoodRepository;
import com.kine.Kine.food.service.repository.Food;
import jakarta.persistence.Id;
import org.springframework.stereotype.Service;
import com.kine.Kine.food.service.dto.FoodDTO;
import org.springframework.data.domain.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.BeanUtils;
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
    public Iterable<FoodDTO> findFoodById(long id){
        Food foundFood = foodRepository.findFoodById(id);
        List<FoodDTO> foodList = new ArrayList<>();

        foodList.add(new FoodDTO(foundFood.getId(), foundFood.getName(), foundFood.getCalories(), foundFood.getProteins(), foundFood.getCarbs(), foundFood.getFat()));
        return foodList;
    }
    public FoodDTO createFood(FoodDTO foodDTO){
        Food food = foodRepository.save(new Food(foodDTO.getName(), foodDTO.getCalories(), foodDTO.getProteins(), foodDTO.getCarbs(), foodDTO.getFat()));
        return new FoodDTO(food.getId(), food.getName(), food.getCalories(), food.getProteins(), food.getCarbs(), food.getFat());
    }

    public FoodDTO updateFood(Long id, UpdateDTO newFoodData) {
        Food existingFood = foodRepository.findFoodById(id);

        // Update only the fields that are provided in the newFoodData object

        if (newFoodData.getName().isPresent()) {
            existingFood.setName(newFoodData.getName().get());
        }
        if (newFoodData.getCalories().isPresent()) {
            existingFood.setCalories(newFoodData.getCalories().get());
        }
        if (newFoodData.getProteins().isPresent()) {
            existingFood.setProteins(newFoodData.getProteins().get());
        }
        if (newFoodData.getCarbs().isPresent()) {
            existingFood.setCarbs(newFoodData.getCarbs().get());
        }
        if (newFoodData.getFat().isPresent()) {
            existingFood.setFat(newFoodData.getFat().get());
        }

        Food food = foodRepository.save(existingFood);
        return new FoodDTO(food.getId(), food.getName(), food.getCalories(), food.getProteins(), food.getCarbs(), food.getFat());
    }
    public void deleteFoodById(Long id) {
        foodRepository.deleteById(id);
    }


}
