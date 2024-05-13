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
@Table(name = "Days")
public class Days implements EntityRepo {
    @Id
    @Column(nullable = false)
    private String id;
    @Column
    private String date;
    @Column
    private Number totalCalories;
    @Column
    private Number totalProtein;
    @Column
    private Number totalCarbs;
    @Column
    private Number totalFats;
    @Column
    private Number targetCalories;
    @Column
    private Number targetProtein;
    @Column
    private Number targetCarbs;
    @Column
    private Number targetFats;
    @Column
    private Timestamp lastChangedAt;
    @Column
    private ChangesType changesType;
}