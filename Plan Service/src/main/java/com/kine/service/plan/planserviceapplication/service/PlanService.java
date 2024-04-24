package com.kine.service.plan.planserviceapplication.service;
import com.kine.service.plan.planserviceapplication.exception.UnrealisticTarget;
import com.kine.service.plan.planserviceapplication.exception.UnrealisticTimeFrame;
import com.kine.service.plan.planserviceapplication.model.Request;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class PlanService {
    public int createPlan(Request request) throws UnrealisticTimeFrame, UnrealisticTarget {
        double expectedChange = (request.getWeight() - request.getTarget())/ request.getTimeHorizon();
        double targetBMI = calculateBMI(request.getWeight(), request.getHeight());

        if (18.5<=targetBMI && targetBMI<=24.9) {
            throw new UnrealisticTarget("The targeted weight isn't within the healthy range");
        }

        if ((0 <= expectedChange && expectedChange <= 1) || (-0.5 <= expectedChange && expectedChange <= 0) ){
            double TDEE = calculateTDEE(calculateBMR(request.getGender(), request.getHeight(), request.getWeight(), request.getBirthdate()), request.getActivityLevel());
            double dailyCaloriesChange = (request.getTarget()-request.getWeight())/ (request.getTimeHorizon()*7);
            return (int) Math.round(TDEE + dailyCaloriesChange);
        }

        throw new UnrealisticTimeFrame("The time frame is not recommended");
    };

    private double calculateBMR(String gender, double height, double weight, String birthdate) {
        System.out.println(birthdate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        double age = Period.between(LocalDate.parse(birthdate, formatter), LocalDate.now()).getYears();
        if (gender.equals("Female")) {
            return 10 * weight + 6.25 * height - 5 * age - 161;
        }
        return 10 * weight + 6.25 * weight - 5 * age + 5;
    };

    private double calculateBMI(double weight, double height) {
      return weight/ Math.pow(height, 2);
    };

    private double calculateTDEE(double BMI, String activityLevel) {
        Map<String, Double> activityCoefficients = Map.of(
                "Sedentary", 1.2,
                "Lightly Active", 1.375,
                "Moderately Active", 1.55,
                "Very Active", 1.725,
                "Extra Active", 1.9
        );
        return BMI*activityCoefficients.get(activityLevel);
    };

}
