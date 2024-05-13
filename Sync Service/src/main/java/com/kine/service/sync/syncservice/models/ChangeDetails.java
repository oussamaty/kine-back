package com.kine.service.sync.syncservice.models;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class ChangeDetails<T> {
    private List<T> created;
    private List<T> updated;
    private List<String> deleted;

    public ChangeDetails() {
        this.created = new ArrayList<>();
        this.updated = new ArrayList<>();
        this.deleted = new ArrayList<>();
    }
}

