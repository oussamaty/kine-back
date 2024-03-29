package com.kine.service.food.dto;
import com.kine.service.food.repository.Food;
import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
public class CreateFoodDTO {
    private long id;
    private String name;
    private float calories;
    private float proteins;
    private float carbs;
    private float fat;

    public CreateFoodDTO(long id, String name, float calories, float proteins, float carbs, float fat) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.proteins = proteins;
        this.carbs = carbs;
        this.fat = fat;
    }

    public Food ConvertToFood() {
        return new Food(this.getId(), this.getName(), this.getCalories(), this.getProteins(), this.getCarbs(), this.getFat());
    }
    public static CreateFoodDTO ConvertToCreateFoodDTO(Food food) {
        return new CreateFoodDTO(food.getId(), food.getName(), food.getCalories(), food.getProteins(), food.getCarbs(), food.getFat());
    }
}
