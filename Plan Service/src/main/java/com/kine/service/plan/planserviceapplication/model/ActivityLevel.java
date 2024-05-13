package com.kine.service.plan.planserviceapplication.model;

import lombok.Getter;

@Getter
public enum ActivityLevel {
    SEDENTARY(1.2),
    LIGHTLY_ACTIVE(1.375) ,
    MODERATELY_ACTIVE(1.55),
    VERY_ACTIVE(1.725),
    EXTRA_ACTIVE(1.9);
    private final double coefficient;

    ActivityLevel(double coefficient){
        this.coefficient = coefficient;
    }

}
