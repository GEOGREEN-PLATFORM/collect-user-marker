package com.example.collect_user_marker.service;

import com.example.collect_user_marker.entity.StatusEntity;
import com.example.collect_user_marker.model.status.StatusDTO;

import java.util.List;

public interface StatusService {
    StatusEntity saveNewStatus(StatusDTO statusDTO);

    List<StatusEntity> getAllStatuses();

    StatusEntity findStatusByCode(String statusCode);

    void deleteStatus(String statusCode);
}
