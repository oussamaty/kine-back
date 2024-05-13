package com.kine.service.sync.syncservice.models;


import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class PullResponse {
    private Map<String, ChangeDetails> changesMap;

    public PullResponse() {
        this.changesMap = new HashMap<>();
    }

    public void put(String tableName, ChangeDetails changeDetails) {
        changesMap.put(tableName, changeDetails);
    }
}
