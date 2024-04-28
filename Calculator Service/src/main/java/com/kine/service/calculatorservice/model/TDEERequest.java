package com.kine.service.calculatorservice.model;

import lombok.*;

import java.sql.Timestamp;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TDEERequest {
    private String gender; // Female or Male
    private Timestamp birthdate;
    private double height; // in cm
    private double weight; // in kg
    private double target; // in kg
    private Timestamp targetDate;
    private String activityLevel; //Sedentary or Lightly Active or Moderately Active or Very Active or Extra Active
}