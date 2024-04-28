package com.kine.service.calculatorservice.model;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TDEEResponse {
    private double BMR;
    private double TDEE;
    private double Target;
}
