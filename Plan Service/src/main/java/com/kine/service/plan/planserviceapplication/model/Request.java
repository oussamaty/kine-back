package com.kine.service.plan.planserviceapplication.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter
public class Request {
    private String gender; // Female or Male
    private String birthdate; // birthdate given in the format DD-MM-YYYY
    private double height; // in cm
    private double weight; // in kg
    private double target; // in kg
    private int timeHorizon; // in weeks
    private String activityLevel; //Sedentary or Lightly Active or Moderately Active or Very Active or Extra Active
    public Request(String gender, String birthdate, double height, double weight, double target, int timeHorizon, String activityLevel) {
        this.gender = gender;
        this.birthdate = birthdate;
        this.height = height;
        this.weight = weight;
        this.target = target;
        this.timeHorizon = timeHorizon;
        this.activityLevel = activityLevel;
    }
}
