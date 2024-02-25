package com.kine.Kine.food.service.service;
import com.kine.Kine.food.service.dto.CreateFoodDTO;
import com.kine.Kine.food.service.dto.GetFoodDTO;
import com.kine.Kine.food.service.dto.UpdateDTO;
import com.kine.Kine.food.service.repository.FoodRepository;
import com.kine.Kine.food.service.repository.Food;
import org.springframework.stereotype.Service;
import com.kine.Kine.food.service.dto.FoodDTO;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Service
public class FoodService {
    private final FoodRepository foodRepository;

    @Autowired
    public FoodService(FoodRepository foodRepository){
        this.foodRepository = foodRepository;
    }
    public Page<GetFoodDTO> getFood(Pageable pageable) {
        Page<Food> foodPage = foodRepository.findAll(pageable);
        return foodPage.map(Food::ConvertToGETFoodDTO);
    }


    public Page<GetFoodDTO> findFoodByName(String name, Pageable pageable) {
        Page<Food> foundFood = foodRepository.findFoodByName(name, pageable);
        return foundFood.map(Food::ConvertToGETFoodDTO);
    }
    public GetFoodDTO findFoodById(long id){
        return foodRepository.findFoodById(id).ConvertToGETFoodDTO();
    }
    public CreateFoodDTO createFood(CreateFoodDTO createFoodDTO){

        Food food = createFoodDTO.ConvertToFood();
        // Check if a food item with the same details already exists
        if (foodRepository.existsByNameAndCaloriesAndProteinsAndCarbsAndFat(food.getName(), food.getCalories(), food.getProteins(), food.getCarbs(), food.getFat())){
            throw new RuntimeException();
        }
        if (!isCaloriesRelationValid(food)) {
            throw new RuntimeException();
        }
        return foodRepository.save(food).ConvertToCreateFoodDTO();
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
        if (!isCaloriesRelationValid(existingFood)) {
            throw new RuntimeException();
        }
        Food food = foodRepository.save(existingFood);
        return new FoodDTO(food.getId(), food.getName(), food.getCalories(), food.getProteins(), food.getCarbs(), food.getFat());
    }
    public void deleteFoodById(Long id) {
        Optional<Food> foodOptional = foodRepository.findById(id);
        if (foodOptional.isPresent()) {
            foodRepository.deleteById(id);
        } else {
            throw new NoSuchElementException();
        }
    }
    private boolean isCaloriesRelationValid(Food food) {
        double calculatedCalories = (food.getCarbs() * 4) + (food.getProteins() * 4) + (food.getFat() * 9);
        // Check if the calculated calories match the provided calories within a small tolerance
        return Math.abs(calculatedCalories - food.getCalories()) < 0.01;
    }

}
