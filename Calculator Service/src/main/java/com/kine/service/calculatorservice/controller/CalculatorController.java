package com.kine.service.calculatorservice.controller;

import com.kine.service.calculatorservice.exceptions.UnrealisticTarget;
import com.kine.service.calculatorservice.exceptions.UnrealisticTimeFrame;
import com.kine.service.calculatorservice.model.TDEERequest;
import com.kine.service.calculatorservice.service.CalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/calculator")
public class CalculatorController {
    private final CalculatorService calculatorService;
    private static final Logger logger = LoggerFactory.getLogger(CalculatorController.class);

    public CalculatorController(CalculatorService calculatorService){
        this.calculatorService = calculatorService;
    }

    @PostMapping
    public ResponseEntity<?> createPlan(@RequestBody TDEERequest request) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(calculatorService.createPlan(request));
        } catch (UnrealisticTimeFrame | UnrealisticTarget e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("An error occurred while resolving the request : {}", request, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}