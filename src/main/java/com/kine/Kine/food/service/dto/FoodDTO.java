package com.kine.Kine.food.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Setter
@Getter
public class FoodDTO {
    private long id;
    private String name;
    private float calories;
    private float proteins;
    private float carbs;
    private float fat;

    public FoodDTO(long id, String name, float calories, float proteins, float carbs, float fat) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.proteins = proteins;
        this.carbs = carbs;
        this.fat = fat;
    }

    public FoodDTO(){};

}
