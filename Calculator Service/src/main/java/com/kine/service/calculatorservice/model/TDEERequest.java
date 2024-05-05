package com.kine.service.calculatorservice.model;


import lombok.*;
import com.kine.service.calculatorservice.constants.Gender;
import com.kine.service.calculatorservice.constants.ActivityLevel;
import java.sql.Timestamp;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TDEERequest {
    private Gender gender; // Female or Male or non specified
    private Timestamp birthdate;
    private double height; // in cm
    private double weight; // in kg
    private double target; // in kg
    private Timestamp targetDate;
    private ActivityLevel activityLevel; //Sedentary or Lightly Active or Moderately Active or Very Active or Extra Active
}