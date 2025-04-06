package com.example.collect_user_marker.service.impl;

import com.example.collect_user_marker.entity.StatusEntity;
import com.example.collect_user_marker.model.status.StatusDTO;
import com.example.collect_user_marker.repository.StatusRepository;
import com.example.collect_user_marker.service.StatusService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Override
    @Transactional
    public StatusEntity saveNewStatus(StatusDTO statusDTO) {

        StatusEntity statusEntity = new StatusEntity();
        statusEntity.setCode(statusDTO.getCode());
        statusEntity.setDescription(statusDTO.getDescription());

        return statusRepository.save(statusEntity);
    }

    @Override
    public List<StatusEntity> getAllStatuses() {
        return statusRepository.findAll();
    }

    @Override
    public StatusEntity findStatusByCode(String statusCode) {
        return statusRepository.findByCode(statusCode);
    }

    @Override
    @Transactional
    public void deleteStatus(String statusCode) {
        statusRepository.deleteByCode(statusCode);
    }
}
