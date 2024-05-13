package com.kine.service.sync.syncservice.repository;
import com.kine.service.sync.syncservice.constants.ChangesType;
import jakarta.persistence.*;
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
@Table(name = "Food")
public class Food {
    @Id
    @Column(nullable = false)
    private Number foodId;
    @Column
    private String name;
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
