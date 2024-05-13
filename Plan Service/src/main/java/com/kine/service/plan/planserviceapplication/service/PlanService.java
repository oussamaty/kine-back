package com.kine.service.plan.planserviceapplication.service;
import com.kine.service.plan.planserviceapplication.exception.UnrealisticTarget;
import com.kine.service.plan.planserviceapplication.exception.UnrealisticTimeFrame;
import com.kine.service.plan.planserviceapplication.model.ActivityLevel;
import com.kine.service.plan.planserviceapplication.model.Gender;
import com.kine.service.plan.planserviceapplication.model.Request;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;


@Service
public class PlanService {
    public int createPlan(Request request) throws UnrealisticTimeFrame, UnrealisticTarget {
        double expectedChange = (request.getWeight() - request.getTarget())/ request.getTimeHorizon();
        double targetBMI = calculateBMI(request.getTarget(), request.getHeight());

        if (18.5>targetBMI || targetBMI>24.9) {
            throw new UnrealisticTarget("The targeted weight isn't within the healthy range");
        }

        if ((0.5 <= expectedChange && expectedChange <= 1) || (-0.5 <= expectedChange && expectedChange <= -0.25) || expectedChange==0){
            double TDEE = calculateTDEE(calculateBMR(request.getGender(), request.getHeight(), request.getWeight(), request.getBirthdate()), request.getActivityLevel());
            double dailyCaloriesChange = (request.getTarget()-request.getWeight())* 7700/ (request.getTimeHorizon()*7);
            return (int) Math.round(TDEE + dailyCaloriesChange);
        }

        throw new UnrealisticTimeFrame("The time frame is not recommended");
    };

    private double calculateBMR(Gender gender, double height, double weight, LocalDate birthdate) {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        int age =  Period.between(birthdate, LocalDate.from(now.toLocalDateTime())).getYears();
        return switch (gender) {
            case FEMALE -> 10 * weight + 6.25 * height - 5 * age - 161;
            case MALE -> 10 * weight + 6.25 * height - 5 * age + 5;
        };
    };

    private double calculateBMI(double weight, double height) {
      return weight / Math.pow(height * Math.pow(10, -2), 2);
    };

    private double calculateTDEE(double BMI, ActivityLevel activityLevel) {
        return BMI*activityLevel.getCoefficient();
    };

}
