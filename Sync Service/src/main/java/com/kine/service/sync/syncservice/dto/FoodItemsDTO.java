package com.kine.service.sync.syncservice.dto;
import com.kine.service.sync.syncservice.constants.ChangesType;
import com.kine.service.sync.syncservice.repository.DailyMeals;
import com.kine.service.sync.syncservice.repository.Days;
import com.kine.service.sync.syncservice.repository.FoodItems;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;

@Setter
@Getter
@AllArgsConstructor
public class FoodItemsDTO implements EntityDTO {
    private String id;
    private String mealId;
    private String foodId;
    private Number quantity;
    private String servingId;
    private Number calories;
    private Number carbs;
    private Number protein;
    private Number fats;

    public FoodItems toEntity(ChangesType changesType) {
        FoodItems entity = new FoodItems();
        entity.setId(this.id);
        entity.setMealId(this.mealId);
        entity.setFoodId(this.foodId);
        entity.setQuantity(this.quantity);
        entity.setServingId(this.servingId);
        entity.setCalories(this.calories);
        entity.setCarbs(this.carbs);
        entity.setProtein(this.protein);
        entity.setFats(this.fats);
        entity.setLastChangedAt(Timestamp.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        entity.setChangesType(changesType);
        return entity;
    }
    public static FoodItemsDTO createDTO(FoodItems foodItems) {
        return new FoodItemsDTO(foodItems.getId(), foodItems.getMealId(), foodItems.getFoodId(), foodItems.getQuantity(), foodItems.getServingId(), foodItems.getCalories(), foodItems.getCarbs(), foodItems.getProtein(), foodItems.getFats());
    }
}
