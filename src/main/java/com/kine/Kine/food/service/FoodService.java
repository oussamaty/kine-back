package com.kine.Kine.food.service;
import com.kine.Kine.food.dto.CreateFoodDTO;
import com.kine.Kine.food.dto.GetFoodDTO;
import com.kine.Kine.food.dto.UpdateDTO;
import com.kine.Kine.food.repository.FoodRepository;
import com.kine.Kine.food.repository.Food;
import org.springframework.stereotype.Service;
import com.kine.Kine.food.dto.FoodDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.kine.Kine.food.exception.*;
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

    public GetFoodDTO findFoodById(long id) throws RessourceNotFoundException {
        Optional<Food> foundFood = foodRepository.findFoodById(id);
        if(foundFood.isEmpty()){
            throw new RessourceNotFoundException("Food with ID " + id + " not found");
        }
        return foundFood.get().ConvertToGETFoodDTO();
    }

    public CreateFoodDTO createFood(CreateFoodDTO createFoodDTO) throws RessourceAlreadyExistException, InvalidDataException{

        Food food = createFoodDTO.ConvertToFood();
        // Check if a food item with the same details already exists
        if (foodRepository.existsByNameAndCaloriesAndProteinsAndCarbsAndFat(food.getName(), food.getCalories(), food.getProteins(), food.getCarbs(), food.getFat())){
            throw new RessourceAlreadyExistException("Food already exists");
        }
        if (isNotCaloriesRelationValid(food)) {
            throw new InvalidDataException("Invalid data");
        }
        return foodRepository.save(food).ConvertToCreateFoodDTO();
    }

    public FoodDTO updateFood(Long id, UpdateDTO newFoodData) throws RessourceNotFoundException, RessourceAlreadyExistException, InvalidDataException {

        Optional<Food> existingFoodOptional = foodRepository.findFoodById(id);

        if (existingFoodOptional.isEmpty()){
            throw new RessourceNotFoundException("Food with ID "  + id +  " not found");
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
        if (foodRepository.existsByNameAndCaloriesAndProteinsAndCarbsAndFat(existingFood.getName(), existingFood.getCalories(), existingFood.getProteins(), existingFood.getCarbs(), existingFood.getFat())){
            throw new RessourceAlreadyExistException("Food already exists");
        }
        Food food = foodRepository.save(existingFood);
        return new FoodDTO(food.getId(), food.getName(), food.getCalories(), food.getProteins(), food.getCarbs(), food.getFat());
    }

    public void deleteFoodById(Long id) throws RessourceNotFoundException {
        Optional<Food> foodOptional = foodRepository.findById(id);
        if (foodOptional.isPresent()) {
            foodRepository.deleteById(id);
        } else {
            throw new RessourceNotFoundException("Food with ID " + id + " not found");
        }
    }

    private boolean isNotCaloriesRelationValid(Food food) {
        double calculatedCalories = (food.getCarbs() * 4) + (food.getProteins() * 4) + (food.getFat() * 9);
        // Check if the calculated calories match the provided calories within a small tolerance
        return !(Math.abs(calculatedCalories - food.getCalories()) < 0.01);
    }

}
