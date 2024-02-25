package com.kine.Kine.food.service.repository;
import com.kine.Kine.food.service.dto.CreateFoodDTO;
import com.kine.Kine.food.service.dto.GetFoodDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name = "Food")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column
    private float calories;
    @Column
    private float proteins;
    @Column
    private float carbs;
    @Column
    private float fat;

    public Food(String name, float calories, float proteins, float carbs, float fat) {
        this.name = name;
        this.calories = calories;
        this.proteins = proteins;
        this.carbs = carbs;
        this.fat = fat;
    }
    public Food(long id, String name, float calories, float proteins, float carbs, float fat) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.proteins = proteins;
        this.carbs = carbs;
        this.fat = fat;
    }
    public Food(){}
    public GetFoodDTO ConvertToGETFoodDTO(){
        return new GetFoodDTO(this.getId(), this.getName(), this.getCalories(), this.getProteins(), this.getCarbs(), this.getFat());
    }

    public CreateFoodDTO ConvertToCreateFoodDTO(){
        return new CreateFoodDTO(this.getId(), this.getName(), this.getCalories(), this.getProteins(), this.getCarbs(), this.getFat());
    }

}
