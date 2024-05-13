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
@Table(name = "Servings")
public class Servings {
    @Id
    @Column(nullable = false)
    private String foodId;
    @Column
    private String name;
    @Column
    private Number amount;
    @Column
    private Timestamp lastChangedAt;
    @Column
    private ChangesType changesType;
}