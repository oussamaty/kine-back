package com.kine.Kine.food.controller;
import com.kine.Kine.food.dto.CreateFoodDTO;
import com.kine.Kine.food.dto.GetFoodDTO;
import com.kine.Kine.food.dto.UpdateDTO;
import com.kine.Kine.food.exception.*;
import com.kine.Kine.food.service.FoodService;
import com.kine.Kine.food.dto.FoodDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;



@RestController
@RequestMapping("/api/food")
public class FoodController {
    private final FoodService foodService;
    public FoodController(FoodService foodService){
        this.foodService = foodService;
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getFoodById(@PathVariable(value = "id") Long id) {
        try {
            GetFoodDTO foodDTO = foodService.findFoodById(id);
            return ResponseEntity.ok(foodDTO); // Return OK status with the found resource
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage()); // Return meaningful response body
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage()); // Handle other exceptions
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
                return ResponseEntity.status(HttpStatus.FOUND).body(foodService.getFood(pageable));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }

    }


    @PostMapping
    public ResponseEntity<?> createFood(@RequestBody CreateFoodDTO createFoodDTO) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(foodService.createFood(createFoodDTO));
        }catch (RessourceAlreadyExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }catch (InvalidDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }catch (Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFood(@PathVariable(value = "id") Long id,
                                            @RequestBody UpdateDTO newFoodData) {
        try{
            return new ResponseEntity<>(foodService.updateFood(id, newFoodData), HttpStatus.ACCEPTED);

        }catch (RessourceAlreadyExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }catch (InvalidDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage()); // Return meaningful response body
        }catch (RuntimeException e){
            return new ResponseEntity<>(new FoodDTO(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFood(@PathVariable Long id) {
        try {
            foodService.deleteFoodById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (RessourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
