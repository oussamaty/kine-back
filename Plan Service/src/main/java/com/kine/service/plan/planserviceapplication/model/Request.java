package com.kine.service.plan.planserviceapplication.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDate;


@ToString
@Getter
@Setter
public class Request {
    private Gender gender; // FEMALE or MALE
    private LocalDate birthdate; // birthdate given in the format DD-MM-YYYY
    private double height; // in cm
    private double weight; // in kg
    private double target; // in kg
    private int timeHorizon; // in weeks
    private ActivityLevel activityLevel; //Sedentary or Lightly Active or Moderately Active or Very Active or Extra Active
    public Request(Gender gender, LocalDate birthdate, double height, double weight, double target, int timeHorizon, ActivityLevel activityLevel) {
        this.gender = gender;
        this.birthdate = birthdate;
        this.height = height;
        this.weight = weight;
        this.target = target;
        this.timeHorizon = timeHorizon;
        this.activityLevel = activityLevel;
    }
}
