package com.kine.service.sync.syncservice.dto;

import com.kine.service.sync.syncservice.constants.ChangesType;
import com.kine.service.sync.syncservice.repository.EntityRepo;

public interface EntityDTO {
    EntityRepo toEntity(ChangesType changesType);
}
