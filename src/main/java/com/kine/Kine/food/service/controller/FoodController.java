package com.kine.Kine.food.service.controller;
import com.kine.Kine.food.service.dto.UpdateDTO;
import com.kine.Kine.food.service.service.FoodService;
import com.kine.Kine.food.service.dto.FoodDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.kine.Kine.food.service.dto.GetFoodDTO;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api/food")
public class FoodController {
    private final FoodService foodService;
    public FoodController(FoodService foodService){
        this.foodService = foodService;
    }


    @GetMapping("/getFood")
    public ResponseEntity<Iterable<GetFoodDTO>> getFood(@RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "id", required = false) Long id) {
        if (id != null) {
            Iterable<GetFoodDTO> foundFood = foodService.findFoodById(id);
            // If the id parameter is provided, search for food by ID
            if(foundFood.iterator().hasNext()) {
                return ResponseEntity.status(HttpStatus.FOUND).body(foundFood);
            }
            else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(foundFood);
            }
        } else if (name != null && !name.isEmpty()) {
            // If the name parameter is provided, search for food by name
            return ResponseEntity.status(HttpStatus.FOUND).body(foodService.findFoodByName(name));
        } else {
            // If neither id nor name parameter is provided, return all food items
            return ResponseEntity.status(HttpStatus.FOUND).body(foodService.getFood());
        }
    }

    @PostMapping("/createFood")
    public FoodDTO createFood(@RequestBody FoodDTO foodDTO) {
        return foodService.createFood(foodDTO);
    }
    @PutMapping("/{id}")
    public FoodDTO updateFood(@PathVariable(value = "id") Long id,
                                            @RequestBody UpdateDTO newFoodData) {
        return foodService.updateFood(id, newFoodData);
    }

    @DeleteMapping("/deleteFood/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable Long id) {
        try {
            foodService.deleteFoodById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NoSuchElementException e) {
            // If the food item with the specified ID does not exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
