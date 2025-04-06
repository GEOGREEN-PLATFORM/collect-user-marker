package com.example.collect_user_marker.service.impl;

import com.example.collect_user_marker.entity.StatusEntity;
import com.example.collect_user_marker.entity.UserMarkerEntity;
import com.example.collect_user_marker.exception.custom.IncorrectDataException;
import com.example.collect_user_marker.exception.custom.ReportNotFoundException;
import com.example.collect_user_marker.exception.custom.StatusNotFoundException;
import com.example.collect_user_marker.feignClient.FeignClientService;
import com.example.collect_user_marker.model.OperatorDetailsDTO;
import com.example.collect_user_marker.model.UserMarkerDTO;
import com.example.collect_user_marker.model.photoAnalyse.PhotoDTO;
import com.example.collect_user_marker.repository.StatusRepository;
import com.example.collect_user_marker.repository.UserMarkerRepository;
import com.example.collect_user_marker.service.UserMarkerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserMarkerServiceImpl implements UserMarkerService {

    @Autowired
    private UserMarkerRepository userMarkerRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private final FeignClientService feignClientService;

    private static final Logger logger = LoggerFactory.getLogger(UserMarkerServiceImpl.class);

    @Override
    @Transactional
    public UserMarkerEntity saveNewReport(UserMarkerDTO userMarkerDTO) {
        UserMarkerEntity userMarkerEntity = toEntity(userMarkerDTO);

        List<UUID> photoIds = userMarkerEntity.getImages();
        Boolean analyseResult = false;

        for (UUID photoId : photoIds) {
            if (analyseResult)
                break;
            analyseResult = feignClientService.analyse(new PhotoDTO(photoId)).getIsHogweed();
        }

        userMarkerEntity.setPhotoVerification(analyseResult);

        logger.debug("Сохранена новая заявка: {}", userMarkerEntity);
        return userMarkerRepository.save(userMarkerEntity);
    }

    @Override
    public List<UserMarkerEntity> getAllReports() {
        return userMarkerRepository.findAll();
    }

    @Override
    public UserMarkerEntity getReportById(UUID id) {
        return userMarkerRepository.findById(id).orElseThrow(
                () -> new ReportNotFoundException(id)
        );
    }

    @Override
    @Transactional
    public UserMarkerEntity updateReport(OperatorDetailsDTO operatorDetailsDTO, UUID id) {
        UserMarkerEntity report = getReportById(id);
        report.setUpdateDate(LocalDate.now());

        report.setOperatorComment(operatorDetailsDTO.getOperatorComment() != null ? operatorDetailsDTO.getOperatorComment() : report.getOperatorComment());

        if (operatorDetailsDTO.getStatusCode() != null) {
            StatusEntity statusEntity = statusRepository.findByCode(operatorDetailsDTO.getStatusCode());
            if (statusEntity != null) {
                report.setStatus(statusEntity);
            }
            else {
                throw new StatusNotFoundException(operatorDetailsDTO.getStatusCode());
            }
        }

        userMarkerRepository.save(report);
        logger.debug("Данные по заявке с айди {} успешно обновлены", id);

        return report;
    }

    private UserMarkerEntity toEntity(UserMarkerDTO dto) {
        UserMarkerEntity entity = new UserMarkerEntity();

        if (dto.getCoordinate() == null || dto.getCoordinate().size() != 2) {
            throw new IncorrectDataException("Dont have X, Y or both coordinates");
        }

        entity.setX(dto.getCoordinate().get(0));
        entity.setY(dto.getCoordinate().get(1));

        try {
            entity.setProblemAreaType(dto.getDetails().getProblemAreaType());
            entity.setUserComment(dto.getDetails().getComment() != null ? dto.getDetails().getComment() : "");
            entity.setImages(dto.getDetails().getImages() != null ? dto.getDetails().getImages() : List.of());
        }
        catch (NullPointerException e) {
            throw new IncorrectDataException(e.getMessage());
        }

        if (dto.getUserDetails() != null) {
            entity.setUserPhone(dto.getUserDetails().getUserPhone() != null ? dto.getUserDetails().getUserPhone() : "");
            entity.setUserEmail(dto.getUserDetails().getUserEmail() != null ? dto.getUserDetails().getUserEmail() : "");
        }

        entity.setOperatorComment("");
        entity.setStatus(statusRepository.findDefaultStatus());

        entity.setCreateDate(LocalDate.now());
        entity.setUpdateDate(LocalDate.now());

        return entity;
    }
}
