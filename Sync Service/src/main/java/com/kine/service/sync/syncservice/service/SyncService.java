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
import java.util.HashMap;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public void push(PushRequest pushRequest) {
        Map<String, ChangeDetails> changesMap = pushRequest.getChangesMap();

        Map<String, JpaRepository<EntityRepo ,String>> classNameMapping = new HashMap<>();
        classNameMapping.put("days", daysRepository);
        classNameMapping.put("daily_meals", dailyMealsRepository);
        classNameMapping.put("food_items", foodItemsRepository);
        classNameMapping.put("food", foodRepository);
        classNameMapping.put("servings", servingsRepository);

        for (Map.Entry<String, ChangeDetails> entry : changesMap.entrySet()) {
            String tableName = entry.getKey();
            ChangeDetails changeDetails = entry.getValue();

            for (EntityDTO createdEntity : changeDetails.getCreated()) {
                createRecord(createdEntity, daysRepository );
            }


            for (EntityDTO updatedEntity : changeDetails.getUpdated()) {
                updateRecord(updatedEntity, );
            }

            for (String deletedEntity : changeDetails.getDeleted()) {
                deleteRecord(deletedEntity, );
            }
        }


    }

    private void createRecord(EntityDTO record, JpaRepository<EntityRepo, String> repository){
        repository.save(record.toEntity(CREATED));
    }

    private void updateRecord(EntityDTO record, JpaRepository<EntityRepo, String> repository){
        repository.save(record.toEntity(UPDATED));
    }

    private void deleteRecord(String recordId, JpaRepository<EntityRepo, String> repository){
        Optional<EntityRepo> existingEntityOptional = repository.findById(recordId);
        if (existingEntityOptional.isEmpty()) {
            return;
        }
        EntityRepo existingEntity = existingEntityOptional.get();
        existingEntity.setChangesType(DELETED);
    }

    private ChangeDetails getChangesForDays(List<Days> entities) {
        ChangeDetails changeDetails = new ChangeDetails();

        for (Days entity : entities) {
            switch (entity.getChangesType()) {
                case CREATED:
                    changeDetails.getCreated().add(new DaysDTO(entity.getId(), entity.getDate(), entity.getTotalCalories(), entity.getTotalProtein(), entity.getTotalCarbs(), entity.getTotalFats(), entity.getTargetCalories(), entity.getTargetProtein(), entity.getTargetCarbs(), entity.getTargetFats()));
                    break;
                case UPDATED:
                    changeDetails.getUpdated().add(new DaysDTO(entity.getId(), entity.getDate(), entity.getTotalCalories(), entity.getTotalProtein(), entity.getTotalCarbs(), entity.getTotalFats(), entity.getTargetCalories(), entity.getTargetProtein(), entity.getTargetCarbs(), entity.getTargetFats()));
                    break;
                case DELETED:
                    changeDetails.getDeleted().add(entity.getDate());
                    break;
            }
        }
        return changeDetails;
    }

    private ChangeDetails getChangesForDailyMeals(List<DailyMeals> entities) {
        ChangeDetails changeDetails = new ChangeDetails();

        for (DailyMeals entity : entities) {
            switch (entity.getChangesType()) {
                case CREATED:
                    changeDetails.getCreated().add(new DailyMealsDTO(entity.getId(), entity.getDayId(), entity.getType(), entity.getTotalCalories(),entity.getTotalProtein() ,entity.getTotalCarbs(), entity.getTotalFats(), entity.getTargetCalories(), entity.getTargetProtein(), entity.getTargetCarbs(), entity.getTargetFats()));
                    break;
                case UPDATED:
                    changeDetails.getUpdated().add(new DailyMealsDTO(entity.getId(), entity.getDayId(), entity.getType(), entity.getTotalCalories(),entity.getTotalProtein() ,entity.getTotalCarbs(), entity.getTotalFats(), entity.getTargetCalories(), entity.getTargetProtein(), entity.getTargetCarbs(), entity.getTargetFats()));
                    break;
                case DELETED:
                    changeDetails.getDeleted().add(entity.getType());
                    break;
            }
        }
        return changeDetails;
    }

    private ChangeDetails getChangesForFood(List<Food> entities) {
        ChangeDetails changeDetails = new ChangeDetails();

        for (Food entity : entities) {
            switch (entity.getChangesType()) {
                case CREATED:
                    changeDetails.getCreated().add(new FoodDTO(entity.getId(), entity.getFoodId(), entity.getName(), entity.getCalories(), entity.getCarbs(), entity.getProtein(), entity.getFats()));
                    break;
                case UPDATED:
                    changeDetails.getUpdated().add(new FoodDTO(entity.getId(), entity.getFoodId(), entity.getName(), entity.getCalories(), entity.getCarbs(), entity.getProtein(), entity.getFats()));
                    break;
                case DELETED:
                    changeDetails.getDeleted().add(entity.getFoodId());
                    break;
            }
        }
        return changeDetails;
    }

    private ChangeDetails getChangesForFoodItems(List<FoodItems> entities) {
        ChangeDetails changeDetails = new ChangeDetails();

        for (FoodItems entity : entities) {
            switch (entity.getChangesType()) {
                case CREATED:
                    changeDetails.getCreated().add(new FoodItemsDTO(entity.getId(), entity.getMealId(), entity.getFoodId(), entity.getQuantity(), entity.getServingId(), entity.getCalories(), entity.getCarbs(), entity.getProtein(), entity.getFats()));
                    break;
                case UPDATED:
                    changeDetails.getUpdated().add(new FoodItemsDTO(entity.getId(), entity.getMealId(), entity.getFoodId(), entity.getQuantity(), entity.getServingId(), entity.getCalories(), entity.getCarbs(), entity.getProtein(), entity.getFats()));
                    break;
                case DELETED:
                    changeDetails.getDeleted().add(entity.getFoodId());
                    break;
            }
        }
        return changeDetails;
    }

    private ChangeDetails getChangesForServing(List<Servings> entities) {
        ChangeDetails changeDetails = new ChangeDetails();

        for (Servings entity : entities) {
            switch (entity.getChangesType()) {
                case CREATED:
                    changeDetails.getCreated().add(new ServingsDTO(entity.getId(), entity.getFoodId(), entity.getName(), entity.getAmount()));
                    break;
                case UPDATED:
                    changeDetails.getUpdated().add(new ServingsDTO(entity.getId(), entity.getFoodId(), entity.getName(), entity.getAmount()));
                    break;
                case DELETED:
                    changeDetails.getDeleted().add(entity.getFoodId());
                    break;
            }
        }
        return changeDetails;
    }
}
