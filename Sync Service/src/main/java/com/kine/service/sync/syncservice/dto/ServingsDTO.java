package com.kine.service.sync.syncservice.dto;
import com.kine.service.sync.syncservice.constants.ChangesType;
import com.kine.service.sync.syncservice.repository.DailyMeals;
import com.kine.service.sync.syncservice.repository.Days;
import com.kine.service.sync.syncservice.repository.Servings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;

@Setter
@Getter
@AllArgsConstructor
public class ServingsDTO implements EntityDTO {
    private String id;
    private String foodId;
    private String name;
    private Number amount;
    public Servings toEntity(ChangesType changesType) {
        Servings entity = new Servings();
        entity.setId(this.id);
        entity.setFoodId(this.foodId);
        entity.setName(this.name);
        entity.setAmount(this.amount);
        entity.setLastChangedAt(Timestamp.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        entity.setChangesType(changesType);
        return entity;
    }
    public static ServingsDTO createDTO(Servings servings) {
        return new ServingsDTO(servings.getId(), servings.getFoodId(), servings.getName(), servings.getAmount());
    }
}
