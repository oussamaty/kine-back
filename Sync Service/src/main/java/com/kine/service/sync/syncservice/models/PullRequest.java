package com.kine.service.sync.syncservice.models;

import lombok.*;
import java.sql.Timestamp;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PullRequest {
    private Timestamp lastPulledAt;
    private int schemaVersion;
}
