package com.kine.service.food.repository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
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
    private Float energie;
    @Column
    private Float calories;
    @Column
    private Float proteins;
    @Column
    private Float carbs;
    @Column
    private Float fat;
    @Column
    private Float saturatedFat;
    @Column
    private Float sugar;
    @Column
    private Float sodium;
    @Column
    private Float salt;
    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Serving> servings;

    public Food(long id, String name, float calories, float proteins, float carbs, float fat) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.proteins = proteins;
        this.carbs = carbs;
        this.fat = fat;
    }
}