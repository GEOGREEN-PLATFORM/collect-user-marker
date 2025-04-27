package com.example.collect_user_marker.service;

import com.example.collect_user_marker.entity.StatusEntity;
import com.example.collect_user_marker.model.ResponseDTO;
import com.example.collect_user_marker.model.status.StatusDTO;

import java.util.List;
import java.util.Optional;

public interface StatusService {
    StatusEntity saveNewStatus(StatusDTO statusDTO);

    List<StatusEntity> getAllStatuses();

    Optional<StatusEntity> findStatusById(Integer id);

    ResponseDTO deleteStatus(Integer id);
}
