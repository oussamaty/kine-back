package com.kine.service.sync.syncservice.dto;

import com.kine.service.sync.syncservice.constants.ChangesType;
import com.kine.service.sync.syncservice.repository.DailyMeals;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;

@Setter
@Getter
@AllArgsConstructor
public class DailyMealsDTO implements EntityDTO {
    private String id;
    private String dayId;
    private String type;
    private Number totalCalories;
    private Number totalProtein;
    private Number totalCarbs;
    private Number totalFats;
    private Number targetCalories;
    private Number targetProtein;
    private Number targetCarbs;
    private Number targetFats;
    @Override
    public DailyMeals toEntity(ChangesType changesType) {
        DailyMeals entity = new DailyMeals();
        entity.setId(this.id);
        entity.setDayId(this.dayId);
        entity.setType(this.type);
        entity.setTotalCalories(this.totalCalories);
        entity.setTotalProtein(this.totalProtein);
        entity.setTotalCarbs(this.totalCarbs);
        entity.setTotalFats(this.totalFats);
        entity.setTargetCalories(this.targetCalories);
        entity.setTargetProtein(this.targetProtein);
        entity.setTargetCarbs(this.targetCarbs);
        entity.setTargetFats(this.targetFats);
        entity.setLastChangedAt(Timestamp.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        entity.setChangesType(changesType);
        return entity;
    }

    public static DailyMealsDTO createDTO(DailyMeals dailyMeals) {
        return new DailyMealsDTO(dailyMeals.getId(), dailyMeals.getDayId(), dailyMeals.getType(), dailyMeals.getTotalCalories(), dailyMeals.getTotalProtein(), dailyMeals.getTotalCarbs(), dailyMeals.getTotalFats(), dailyMeals.getTargetCalories(), dailyMeals.getTargetProtein(), dailyMeals.getTargetCarbs(), dailyMeals.getTargetFats());
    }
}
