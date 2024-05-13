package com.kine.service.sync.syncservice.models;
import com.kine.service.sync.syncservice.dto.EntityDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class ChangeDetails {
    private List<EntityDTO> created;
    private List<EntityDTO> updated;
    private List<String> deleted;

    public ChangeDetails() {
        this.created = new ArrayList<>();
        this.updated = new ArrayList<>();
        this.deleted = new ArrayList<>();
    }
}

