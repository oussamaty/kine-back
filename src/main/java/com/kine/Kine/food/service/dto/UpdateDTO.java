package com.kine.Kine.food.service.dto;
import lombok.Getter;
import java.util.Optional;

@Getter
public class UpdateDTO {
    private Optional<String> name;
    private Optional<Float> calories;
    private Optional<Float> proteins;
    private Optional<Float> carbs;
    private Optional<Float> fat;

    public UpdateDTO(Optional<String> name, Optional<Float> calories, Optional<Float> proteins, Optional<Float> carbs, Optional<Float> fat) {
        this.name = name;
        this.calories = calories;
        this.proteins = proteins;
        this.carbs = carbs;
        this.fat = fat;
    }

    public void setName(String name) {
        this.name = Optional.ofNullable(name);
    }

    public void setCalories(Float calories) {
        this.calories = Optional.ofNullable(calories);
    }

    public void setProteins(Float proteins) {
        this.proteins = Optional.ofNullable(proteins);
    }

    public void setCarbs(Float carbs) {
        this.carbs = Optional.ofNullable(carbs);
    }

    public void setFat(Float fat) {
        this.fat = Optional.ofNullable(fat);
    }


}
