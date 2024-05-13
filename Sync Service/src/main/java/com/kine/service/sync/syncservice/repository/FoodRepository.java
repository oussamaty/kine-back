package com.kine.service.sync.syncservice.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


@Repository
public interface FoodRepository extends JpaRepository<Food, Number> {
    List<Food> findByLastChangedAtAfter(Timestamp timestamp);
}
