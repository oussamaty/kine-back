package com.kine.service.sync.syncservice.repository;

import com.kine.service.sync.syncservice.constants.ChangesType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "FoodItems")
public class FoodItems implements EntityRepo {
    @Id
    @Column
    private String id;
    @Column
    private String mealId;
    @Column
    private String FoodId;
    @Column
    private Number quantity;
    @Column
    private String ServingId;
    @Column
    private Number calories;
    @Column
    private Number carbs;
    @Column
    private Number protein;
    @Column
    private Number fats;
    @Column
    private Timestamp lastChangedAt;
    @Column
    private ChangesType changesType;
}
