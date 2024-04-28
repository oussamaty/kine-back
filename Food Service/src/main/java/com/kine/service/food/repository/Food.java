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
    private float energie;
    @Column
    private float calories;
    @Column
    private float proteins;
    @Column
    private float carbs;
    @Column
    private float fat;
    @Column
    private float saturatedFat;
    @Column
    private float sugar;
    @Column
    private float sodium;
    @Column
    private float salt;
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