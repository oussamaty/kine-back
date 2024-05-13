package com.kine.service.sync.syncservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;


@Repository
public interface DailyMealsRepository extends JpaRepository<DailyMeals, String> {
    List<DailyMeals> findByLastChangedAtAfter(Timestamp timestamp);
}
