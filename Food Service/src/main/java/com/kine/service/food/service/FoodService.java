package com.kine.service.food.service;
import com.kine.service.food.dto.CreateFoodDTO;
import com.kine.service.food.dto.GetFoodDTO;
import com.kine.service.food.dto.UpdateFoodDTO;
import com.kine.service.food.dto.FoodDTO;
import com.kine.service.food.repository.FoodRepository;
import com.kine.service.food.repository.Food;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.kine.service.food.exception.*;
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
        return foodPage.map(GetFoodDTO::ConvertToGETFoodDTO);
    }

    public Page<GetFoodDTO> findFoodByName(String name, Pageable pageable) {
        Page<Food> foundFood = foodRepository.findFoodByName(name, pageable);
        return foundFood.map(GetFoodDTO::ConvertToGETFoodDTO);
    }

    public GetFoodDTO findFoodById(long id) throws ResourceNotFoundException {
        Optional<Food> foundFood = foodRepository.findFoodById(id);
        if (foundFood.isEmpty()) {
            throw new ResourceNotFoundException("Food with ID " + id + " not found");
        }
        return GetFoodDTO.ConvertToGETFoodDTO(foundFood.get());
    }

    public CreateFoodDTO createFood(CreateFoodDTO createFoodDTO) throws RessourceAlreadyExistException, InvalidDataException{

        Food food = createFoodDTO.ConvertToFood();
        // Check if a food item with the same details already exists
        if (foodRepository.existsByNameAndCaloriesAndProteinsAndCarbsAndFat(food.getName(), food.getCalories(), food.getProteins(), food.getCarbs(), food.getFat())) {
            throw new RessourceAlreadyExistException("Food already exists");
        }
        if (isNotCaloriesRelationValid(food)) {
            throw new InvalidDataException("Invalid data");
        }
        return CreateFoodDTO.ConvertToCreateFoodDTO(foodRepository.save(food));
    }

    public FoodDTO updateFood(Long id, UpdateFoodDTO newFoodData) throws ResourceNotFoundException, RessourceAlreadyExistException, InvalidDataException {

        Optional<Food> existingFoodOptional = foodRepository.findFoodById(id);

        if (existingFoodOptional.isEmpty()) {
            throw new ResourceNotFoundException("Food with ID "  + id +  " not found");
        }

        Food existingFood = existingFoodOptional.get();

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

        if (isNotCaloriesRelationValid(existingFood)) {
            throw new InvalidDataException("Invalid data");
        }
        if (foodRepository.existsByNameAndCaloriesAndProteinsAndCarbsAndFat(existingFood.getName(), existingFood.getCalories(), existingFood.getProteins(), existingFood.getCarbs(), existingFood.getFat())) {
            throw new RessourceAlreadyExistException("Food already exists, delete the food with the id " + id + " or keep it and use the already existing one.");
        }
        Food food = foodRepository.save(existingFood);
        return new FoodDTO(food.getId(), food.getName(), food.getCalories(), food.getProteins(), food.getCarbs(), food.getFat());
    }

    public void deleteFoodById(Long id) throws ResourceNotFoundException {
        Optional<Food> foodOptional = foodRepository.findById(id);
        if (foodOptional.isPresent()) {
            foodRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Food with ID " + id + " not found");
        }
    }

    private boolean isNotCaloriesRelationValid(Food food) {
        float calculatedCalories = (food.getCarbs() * 4) + (food.getProteins() * 4) + (food.getFat() * 9);
        // Check if the calculated calories match the provided calories within a small tolerance
        return !(Math.abs(calculatedCalories - food.getCalories()) < 0.01);
    }


}