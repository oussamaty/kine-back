package com.kine.Kine.food.service.dto;

import lombok.Getter;

public class FoodDTO {
    private long food_id;
    @Getter
    private String name;
    private float calories;
    private float proteins;

    public FoodDTO(long food_id, String name, float calories, float proteins, float carbs, float fat) {
        this.food_id = food_id;
        this.name = name;
        this.calories = calories;
        this.proteins = proteins;
        this.carbs = carbs;
        this.fat = fat;
    }

    private float carbs;

    public void setName(String name) {
        this.name = name;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public float getProteins() {
        return proteins;
    }

    public void setProteins(float proteins) {
        this.proteins = proteins;
    }

    public float getCarbs() {
        return carbs;
    }

    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    private float fat;
}
