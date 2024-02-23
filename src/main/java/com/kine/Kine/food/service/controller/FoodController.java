package com.kine.Kine.food.service.controller;
import com.kine.Kine.food.service.dto.UpdateDTO;
import com.kine.Kine.food.service.service.FoodService;
import com.kine.Kine.food.service.dto.FoodDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/food")
public class FoodController {
    private final FoodService foodService;
    public FoodController(FoodService foodService){
        this.foodService = foodService;
    }


    @GetMapping("/getFood")
    public Iterable<FoodDTO> getFood(@RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "id", required = false) Long id) {
        if (id != null) {
            // If the id parameter is provided, search for food by ID
            return foodService.findFoodById(id);
        } else if (name != null && !name.isEmpty()) {
            // If the name parameter is provided, search for food by name
            return foodService.findFoodByName(name);
        } else {
            // If neither id nor name parameter is provided, return all food items
            return foodService.getFood();
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
        foodService.deleteFoodById(id);
        return ResponseEntity.noContent().build();
    }

}
