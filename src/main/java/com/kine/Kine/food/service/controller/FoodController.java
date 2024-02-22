package com.kine.Kine.food.service.controller;

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

    @GetMapping("/food_all")
    public Iterable<FoodDTO> getFood_All() {
        return this.foodService.getFood_All();
    }

}
