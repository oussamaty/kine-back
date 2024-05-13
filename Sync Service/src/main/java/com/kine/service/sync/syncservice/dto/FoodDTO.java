package com.kine.service.sync.syncservice.dto;

import com.kine.service.sync.syncservice.constants.ChangesType;
import com.kine.service.sync.syncservice.repository.Food;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;

@Setter
@Getter
@AllArgsConstructor
public class FoodDTO {
    private Number foodId;
    private String name;
    private Number calories;
    private Number carbs;
    private Number protein;
    private Number fats;

    public Food toEntity(ChangesType changesType) {
        Food entity = new Food();
        entity.setFoodId(this.foodId);
        entity.setName(this.name);
        entity.setCalories(this.calories);
        entity.setCarbs(this.carbs);
        entity.setProtein(this.protein);
        entity.setFats(this.fats);
        entity.setLastChangedAt(Timestamp.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        entity.setChangesType(changesType);
        return entity;
    }
}
