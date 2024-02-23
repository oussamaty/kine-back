package com.kine.Kine.food.service.dto;

import java.util.Optional;

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

    public Optional<String> getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Optional.ofNullable(name);
    }

    public Optional<Float> getCalories() {
        return calories;
    }

    public void setCalories(Float calories) {
        this.calories = Optional.ofNullable(calories);
    }

    public Optional<Float> getProteins() {
        return proteins;
    }

    public void setProteins(Float proteins) {
        this.proteins = Optional.ofNullable(proteins);
    }

    public Optional<Float> getCarbs() {
        return carbs;
    }

    public void setCarbs(Float carbs) {
        this.carbs = Optional.ofNullable(carbs);
    }

    public Optional<Float> getFat() {
        return fat;
    }

    public void setFat(Float fat) {
        this.fat = Optional.ofNullable(fat);
    }


}
