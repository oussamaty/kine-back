package com.kine.Kine.food.service.controller;
import com.kine.Kine.food.service.dto.CreateFoodDTO;
import com.kine.Kine.food.service.dto.UpdateDTO;
import com.kine.Kine.food.service.service.FoodService;
import com.kine.Kine.food.service.dto.FoodDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.kine.Kine.food.service.dto.GetFoodDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api/food")
public class FoodController {
    private final FoodService foodService;
    public FoodController(FoodService foodService){
        this.foodService = foodService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<GetFoodDTO > searchFoodById(@PathVariable(value = "id") Long id) {
        try{
            return ResponseEntity.status(HttpStatus.FOUND).body(foodService.findFoodById(id));
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GetFoodDTO());
        }
    }


    @GetMapping(value = "{name}")
    public ResponseEntity<Page<GetFoodDTO>> searchFoodByName(
            @PathVariable(value = "name") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<GetFoodDTO> foods = foodService.findFoodByName(name, pageable);
            return ResponseEntity.status(HttpStatus.OK).body(foods);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Page.empty());
        }
    }

    @GetMapping
    public ResponseEntity<Page<GetFoodDTO>> getFood(@RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
            Pageable pageable = PageRequest.of(page, size);
            Page<GetFoodDTO> foodPage = foodService.getFood(pageable);
            return ResponseEntity.status(HttpStatus.FOUND).body(foodPage);
        }


    @PostMapping
    public ResponseEntity<CreateFoodDTO> createFood(@RequestBody CreateFoodDTO createFoodDTO) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(foodService.createFood(createFoodDTO));
        }catch (RuntimeException e){
            return new ResponseEntity<>(createFoodDTO, HttpStatus.CONFLICT);
        }

    }

    @PutMapping("{id}")
    public ResponseEntity<FoodDTO> updateFood(@PathVariable(value = "id") Long id,
                                            @RequestBody UpdateDTO newFoodData) {
        try{
            return new ResponseEntity<>(foodService.updateFood(id, newFoodData), HttpStatus.ACCEPTED);
        }catch (RuntimeException e){
            return new ResponseEntity<>(new FoodDTO(), HttpStatus.CONFLICT);
    }

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable Long id) {
        try {
            foodService.deleteFoodById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NoSuchElementException e) {
            // If the food item with the specified ID does not exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
