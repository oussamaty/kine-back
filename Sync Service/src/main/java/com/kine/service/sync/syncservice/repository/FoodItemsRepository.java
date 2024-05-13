package com.kine.service.sync.syncservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;


@Repository
public interface FoodItemsRepository extends JpaRepository<FoodItems, String> {
    List<FoodItems> findByLastChangedAtAfter(Timestamp timestamp);
}
