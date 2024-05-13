package com.kine.service.sync.syncservice.dto;
import com.kine.service.sync.syncservice.constants.ChangesType;
import com.kine.service.sync.syncservice.repository.Days;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;

@Setter
@Getter
@AllArgsConstructor
public class DaysDTO {
    private String date;
    private Number totalCalories;
    private Number totalProtein;
    private Number totalCarbs;
    private Number totalFats;
    private Number targetCalories;
    private Number targetProtein;
    private Number targetCarbs;
    private Number targetFats;

    public Days toEntity(ChangesType changesType) {
        Days entity = new Days();
        entity.setDate(this.date);
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
}
