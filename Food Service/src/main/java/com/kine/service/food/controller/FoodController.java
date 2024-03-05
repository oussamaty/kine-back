package com.kine.service.food.controller;
import com.kine.service.food.dto.CreateFoodDTO;
import com.kine.service.food.dto.GetFoodDTO;
import com.kine.service.food.dto.UpdateFoodDTO;
import com.kine.service.food.exception.*;
import com.kine.service.food.service.FoodService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api/v1/food")
public class FoodController {
    private final FoodService foodService;
    private static final Logger logger = LoggerFactory.getLogger(FoodController.class);
    public FoodController(FoodService foodService){
        this.foodService = foodService;
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getFoodById(@PathVariable(value = "id") Long id) {
        try {
            GetFoodDTO foodDTO = foodService.findFoodById(id);
            return ResponseEntity.status(HttpStatus.OK).body(foodDTO); // Return OK status with the found resource
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage()); // Return meaningful response body
        } catch (Exception e) {
            // Log the exception internally for debugging purposes
            logger.error("An error occurred while retrieving food with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred"); // Return generic error message to the client
        }
    }


    @GetMapping
    public ResponseEntity<?> searchFood(@RequestParam(value = "name", required = false) String name,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            if (name != null) {
                return ResponseEntity.status(HttpStatus.OK).body(foodService.findFoodByName(name, pageable));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(foodService.getFood(pageable));
            }
        } catch (Exception e) {
            // Log the exception internally for debugging purposes
            logger.error("An error occurred while retrieving food ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred"); // Return generic error message to the client
        }
    }


    @PostMapping
    public ResponseEntity<?> createFood(@RequestBody CreateFoodDTO createFoodDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(foodService.createFood(createFoodDTO));
        } catch (RessourceAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (InvalidDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Log the exception internally for debugging purposes
            logger.error("An error occurred while creating the food : {}", createFoodDTO, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred"); // Return generic error message to the client
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFood(@PathVariable(value = "id") Long id,
                                        @RequestBody UpdateFoodDTO newFoodData) {
        try{
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(foodService.updateFood(id, newFoodData));
        } catch (RessourceAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (InvalidDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage()); // Return meaningful response body
        } catch (Exception e) {
            // Log the exception internally for debugging purposes
            logger.error("An error occurred while creating the food : {}, with the new data: {}", id, newFoodData , e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred"); // Return generic error message to the client
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFood(@PathVariable Long id) {
        try {
            foodService.deleteFoodById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            // Log the exception internally for debugging purposes
            logger.error("An error occurred while deleting the food : {}", id , e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred"); // Return generic error message to the client
        }
    }
}