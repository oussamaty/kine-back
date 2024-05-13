package com.kine.service.sync.syncservice.repository;

import com.kine.service.sync.syncservice.constants.ChangesType;

public interface EntityRepo {
    void setChangesType(ChangesType changesType);
    ChangesType getChangesType();
}
