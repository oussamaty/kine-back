package com.kine.service.sync.syncservice.controller;

import com.kine.service.sync.syncservice.models.PullRequest;
import com.kine.service.sync.syncservice.models.PushRequest;
import com.kine.service.sync.syncservice.service.SyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.sql.Timestamp;

@RestController
@RequestMapping("/api/v1/sync")
public class SyncController {
    private final SyncService syncService;
    private static final Logger logger = LoggerFactory.getLogger(SyncController.class);
    public SyncController(SyncService syncService){
        this.syncService = syncService;
    }

    @GetMapping
    public ResponseEntity<?> pull(@RequestBody PullRequest pullRequest) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(syncService.pull(pullRequest));
        } catch (Exception e) {
            logger.error("An error occurred while retrieving data", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PostMapping
    public ResponseEntity<?> push(@RequestBody PushRequest pushRequest) {
        try{
            syncService.push(pushRequest);
            return ResponseEntity.status(HttpStatus.OK).body("Success");
        } catch (Exception e) {
            logger.error("An error occurred while retrieving data", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
