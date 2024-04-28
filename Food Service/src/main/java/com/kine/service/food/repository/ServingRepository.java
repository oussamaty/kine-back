package com.kine.service.food.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServingRepository extends JpaRepository<Serving, Long> {
    Optional<Serving> findServingById(long id);

}