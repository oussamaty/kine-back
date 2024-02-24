package com.kine.Kine.food.service.service;
import com.kine.Kine.food.service.dto.GetFoodDTO;
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
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FoodService {
    private final FoodRepository foodRepository;
    @Autowired
    public FoodService(FoodRepository foodRepository){
        this.foodRepository = foodRepository;
    }
    public Iterable<GetFoodDTO> getFood() {
        Iterable<Food> foundFood = foodRepository.findAll();
        List<GetFoodDTO> foodList = new ArrayList<>();
        for (Food food : foundFood)
            foodList.add( food.ConvertToGETFoodDTO() );
        return foodList;
    }

    public Iterable<GetFoodDTO> findFoodByName(String name){
        Iterable<Food> foundFood = foodRepository.findFoodByName(name);
        List<GetFoodDTO> foodList = new ArrayList<>();
        for (Food food : foundFood)
            foodList.add(food.ConvertToGETFoodDTO());
        return foodList;
    }
    public Iterable<GetFoodDTO> findFoodById(long id){
        try {
            Food foundFood = foodRepository.findFoodById(id);
            List<GetFoodDTO> foodList = new ArrayList<>();
            foodList.add(foundFood.ConvertToGETFoodDTO());
            return foodList;
        }catch (Exception e){
            return new ArrayList<>();
        }
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
        Optional<Food> foodOptional = foodRepository.findById(id);
        if (foodOptional.isPresent()) {
            foodRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Food item with id " + id + " not found");
        }
    }


}
