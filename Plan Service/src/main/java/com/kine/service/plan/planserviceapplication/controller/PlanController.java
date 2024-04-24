package com.kine.service.plan.planserviceapplication.controller;
import com.kine.service.plan.planserviceapplication.exception.UnrealisticTarget;
import com.kine.service.plan.planserviceapplication.exception.UnrealisticTimeFrame;
import com.kine.service.plan.planserviceapplication.model.Request;
import com.kine.service.plan.planserviceapplication.service.PlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/plan")
public class PlanController {
    private final PlanService planService;
    private static final Logger logger = LoggerFactory.getLogger(PlanController.class);

    public PlanController(PlanService planService){
        this.planService = planService;
    }

    @GetMapping
    public ResponseEntity<?> createPlan(@RequestBody Request request) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(planService.createPlan(request));
        } catch (UnrealisticTimeFrame | UnrealisticTarget e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            System.out.println(request);
            // Log the exception internally for debugging purposes
            logger.error("An error occurred while resolving the request : {}", request, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
