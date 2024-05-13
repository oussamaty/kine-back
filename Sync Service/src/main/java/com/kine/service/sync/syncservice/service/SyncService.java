package com.kine.service.sync.syncservice.service;

import com.kine.service.sync.syncservice.constants.ChangesType;
import com.kine.service.sync.syncservice.dto.*;
import com.kine.service.sync.syncservice.models.ChangeDetails;
import com.kine.service.sync.syncservice.models.PullRequest;
import com.kine.service.sync.syncservice.models.PullResponse;
import com.kine.service.sync.syncservice.models.PushRequest;
import com.kine.service.sync.syncservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

import static com.kine.service.sync.syncservice.constants.ChangesType.*;

@Service
public class SyncService {
    private final DaysRepository daysRepository;
    private final DailyMealsRepository dailyMealsRepository;
    private final FoodItemsRepository foodItemsRepository;
    private final FoodRepository foodRepository;
    private final ServingsRepository servingsRepository;

    @Autowired
    public SyncService(DaysRepository daysRepository, DailyMealsRepository dailyMealsRepository, FoodItemsRepository foodItemsRepository, FoodRepository foodRepository, ServingsRepository servingsRepository) {
        this.daysRepository = daysRepository;
        this.dailyMealsRepository = dailyMealsRepository;
        this.foodItemsRepository = foodItemsRepository;
        this.foodRepository = foodRepository;
        this.servingsRepository = servingsRepository;
    }

    public PullResponse pull(PullRequest pullRequest) {
        PullResponse pullResponse = new PullResponse();

        pullResponse.put("days", getChangesForDays(daysRepository.findByLastChangedAtAfter(pullRequest.getLastPulledAt())));
        pullResponse.put("daily_meals", getChangesForDailyMeals(dailyMealsRepository.findByLastChangedAtAfter(pullRequest.getLastPulledAt())));
        pullResponse.put("food_items", getChangesForFoodItems(foodItemsRepository.findByLastChangedAtAfter(pullRequest.getLastPulledAt())));
        pullResponse.put("food", getChangesForFood(foodRepository.findByLastChangedAtAfter(pullRequest.getLastPulledAt())));
        pullResponse.put("servings", getChangesForServing(servingsRepository.findByLastChangedAtAfter(pullRequest.getLastPulledAt())));

        return pullResponse;
    }

    public PushResponse push(PushRequest pushRequest) {

    }

    private <T1> void createRecord(T1 record, JpaRepository repository){
        repository.save(record.toEntity(CREATED));
    }

    private <T1, T2> void updateRecord(T1 record, T2 repository){
        repository.save(record.toEntity(UPDATED));
    }


    private ChangeDetails<DaysDTO> getChangesForDays(List<Days> entities) {
        ChangeDetails<DaysDTO> changeDetails = new ChangeDetails<DaysDTO>();

        for (Days entity : entities) {
            switch (entity.getChangesType()) {
                case CREATED:
                    changeDetails.getCreated().add(new DaysDTO(entity.getDate(), entity.getTotalCalories(), entity.getTotalProtein(), entity.getTotalCarbs(), entity.getTotalFats(), entity.getTargetCalories(), entity.getTargetProtein(), entity.getTargetCarbs(), entity.getTargetFats()));
                    break;
                case UPDATED:
                    changeDetails.getUpdated().add(new DaysDTO(entity.getDate(), entity.getTotalCalories(), entity.getTotalProtein(), entity.getTotalCarbs(), entity.getTotalFats(), entity.getTargetCalories(), entity.getTargetProtein(), entity.getTargetCarbs(), entity.getTargetFats()));
                    break;
                case DELETED:
                    changeDetails.getDeleted().add(entity.getDate());
                    break;
            }
        }
        return changeDetails;
    }

    private ChangeDetails<DailyMealsDTO> getChangesForDailyMeals(List<DailyMeals> entities) {
        ChangeDetails<DailyMealsDTO> changeDetails = new ChangeDetails<DailyMealsDTO>();

        for (DailyMeals entity : entities) {
            switch (entity.getChangesType()) {
                case CREATED:
                    changeDetails.getCreated().add(new DailyMealsDTO(entity.getDayId(), entity.getType(), entity.getTotalCalories(),entity.getTotalProtein() ,entity.getTotalCarbs(), entity.getTotalFats(), entity.getTargetCalories(), entity.getTargetProtein(), entity.getTargetCarbs(), entity.getTargetFats()));
                    break;
                case UPDATED:
                    changeDetails.getUpdated().add(new DailyMealsDTO(entity.getDayId(), entity.getType(), entity.getTotalCalories(),entity.getTotalProtein() ,entity.getTotalCarbs(), entity.getTotalFats(), entity.getTargetCalories(), entity.getTargetProtein(), entity.getTargetCarbs(), entity.getTargetFats()));
                    break;
                case DELETED:
                    changeDetails.getDeleted().add(entity.getType());
                    break;
            }
        }
        return changeDetails;
    }

    private ChangeDetails<FoodDTO> getChangesForFood(List<Food> entities) {
        ChangeDetails<FoodDTO> changeDetails = new ChangeDetails<FoodDTO>();

        for (Food entity : entities) {
            switch (entity.getChangesType()) {
                case CREATED:
                    changeDetails.getCreated().add(new FoodDTO(entity.getFoodId(), entity.getName(), entity.getCalories(), entity.getCarbs(), entity.getProtein(), entity.getFats()));
                    break;
                case UPDATED:
                    changeDetails.getUpdated().add(new FoodDTO(entity.getFoodId(), entity.getName(), entity.getCalories(), entity.getCarbs(), entity.getProtein(), entity.getFats()));
                    break;
                case DELETED:
                    changeDetails.getDeleted().add(entity.getFoodId().toString());
                    break;
            }
        }
        return changeDetails;
    }

    private ChangeDetails<FoodItemsDTO> getChangesForFoodItems(List<FoodItems> entities) {
        ChangeDetails<FoodItemsDTO> changeDetails = new ChangeDetails<FoodItemsDTO>();

        for (FoodItems entity : entities) {
            switch (entity.getChangesType()) {
                case CREATED:
                    changeDetails.getCreated().add(new FoodItemsDTO(entity.getMealId(), entity.getFoodId(), entity.getQuantity(), entity.getServingId(), entity.getCalories(), entity.getCarbs(), entity.getProtein(), entity.getFats()));
                    break;
                case UPDATED:
                    changeDetails.getUpdated().add(new FoodItemsDTO(entity.getMealId(), entity.getFoodId(), entity.getQuantity(), entity.getServingId(), entity.getCalories(), entity.getCarbs(), entity.getProtein(), entity.getFats()));
                    break;
                case DELETED:
                    changeDetails.getDeleted().add(entity.getFoodId());
                    break;
            }
        }
        return changeDetails;
    }

    private ChangeDetails<ServingsDTO> getChangesForServing(List<Servings> entities) {
        ChangeDetails<ServingsDTO> changeDetails = new ChangeDetails<ServingsDTO>();

        for (Servings entity : entities) {
            switch (entity.getChangesType()) {
                case CREATED:
                    changeDetails.getCreated().add(new ServingsDTO(entity.getFoodId(), entity.getName(), entity.getAmount()));
                    break;
                case UPDATED:
                    changeDetails.getUpdated().add(new ServingsDTO(entity.getFoodId(), entity.getName(), entity.getAmount()));
                    break;
                case DELETED:
                    changeDetails.getDeleted().add(entity.getFoodId());
                    break;
            }
        }
        return changeDetails;
    }
}
