package com.kine.service.calculatorservice.service;


import com.kine.service.calculatorservice.exceptions.UnrealisticTarget;
import com.kine.service.calculatorservice.exceptions.UnrealisticTimeFrame;
import com.kine.service.calculatorservice.model.TDEERequest;
import com.kine.service.calculatorservice.model.TDEEResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Service
public class CalculatorService {
    public TDEEResponse createPlan(TDEERequest request) throws UnrealisticTimeFrame, UnrealisticTarget {
        LocalDate targetLocalDate = request.getTargetDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentLocalDate = Instant.now().atZone(ZoneId.systemDefault()).toLocalDate();;
        LocalDate birthLocalDate = request.getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();;

        long timeHorizon = ChronoUnit.WEEKS.between(currentLocalDate, targetLocalDate);
        long age = ChronoUnit.YEARS.between(birthLocalDate, currentLocalDate);

        double expectedChange = (request.getTarget() - request.getWeight())/ timeHorizon;
        double targetBMI = calculateBMI(request.getTarget(), request.getHeight());


        if (16>=targetBMI || targetBMI>=28) {
            throw new UnrealisticTarget("The targeted weight isn't within the healthy range");
        }

        if (-0.5 <= expectedChange && expectedChange <= 1){
            double BMR = calculateBMR(request.getGender(), request.getHeight(), request.getWeight(), age);
            double TDEE = calculateTDEE(BMR, request.getActivityLevel());
            double dailyCaloriesChange = 7170 * expectedChange/7;
            double Target = Math.round(TDEE + dailyCaloriesChange);
            return new TDEEResponse(BMR, TDEE, Target);
        }

        throw new UnrealisticTimeFrame("The time frame is not recommended");
    }

    private double calculateBMR(String gender, double height, double weight, long age) {
        if (gender.equals("Female")) {
            return 10 * weight + 6.25 * height - 5 * age - 161;
        }
        return 10 * weight + 6.25 * height - 5 * age + 5;
    }

    private double calculateBMI(double weight, double height) {
        return 10000 * weight/ (height * height);
    }

    private double calculateTDEE(double BMI, String activityLevel) {
        Map<String, Double> activityCoefficients = Map.of(
                "Sedentary", 1.2,
                "Lightly Active", 1.375,
                "Moderately Active", 1.55,
                "Very Active", 1.725,
                "Extra Active", 1.9
        );
        return BMI*activityCoefficients.get(activityLevel);
    }

}
