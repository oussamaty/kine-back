package com.kine.Kine.food.service.controller;
import org.springframework.http.HttpStatus;
import com.kine.Kine.food.service.service.FoodService;
import com.kine.Kine.food.service.dto.FoodDTO;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/food")
public class FoodController {
    private final FoodService foodService;
    public FoodController(FoodService foodService){
        this.foodService = foodService;
    }

    @GetMapping("/getFood")
    public Iterable<FoodDTO> getFood(@RequestParam(value = "name", required = false) String name) {
        if (name != null && !name.isEmpty()) {
            // If the name parameter is provided, search for food by name
            return foodService.findFoodByName(name);
        } else {
            // If no name parameter is provided, return all food items
            return foodService.getFood();
        }
    }
    @PostMapping("/createFood")
    public FoodDTO createFood(@RequestBody FoodDTO foodDTO) {
        return foodService.createFood(foodDTO);
    }
}
