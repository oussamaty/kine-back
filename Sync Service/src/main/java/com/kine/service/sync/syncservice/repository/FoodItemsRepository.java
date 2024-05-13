package com.kine.service.sync.syncservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


@Repository
public interface FoodItemsRepository extends JpaRepository<FoodItems, String>  {
    List<FoodItems> findByLastChangedAtAfter(Timestamp timestamp);
}
