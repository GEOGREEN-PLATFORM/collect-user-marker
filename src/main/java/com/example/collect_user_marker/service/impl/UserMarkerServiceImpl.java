package com.example.collect_user_marker.service.impl;

import com.example.collect_user_marker.entity.ProblemTypeEntity;
import com.example.collect_user_marker.entity.StatusEntity;
import com.example.collect_user_marker.entity.UserMarkerEntity;
import com.example.collect_user_marker.exception.custom.IncorrectDataException;
import com.example.collect_user_marker.exception.custom.ProblemNotFoundException;
import com.example.collect_user_marker.exception.custom.ReportNotFoundException;
import com.example.collect_user_marker.exception.custom.StatusNotFoundException;
import com.example.collect_user_marker.feignClient.FeignClientService;
import com.example.collect_user_marker.model.OperatorDetailsDTO;
import com.example.collect_user_marker.model.UserMarkerDTO;
import com.example.collect_user_marker.model.image.ImageDTO;
import com.example.collect_user_marker.model.photoAnalyse.PhotoDTO;
import com.example.collect_user_marker.repository.ProblemTypeRepository;
import com.example.collect_user_marker.repository.StatusRepository;
import com.example.collect_user_marker.repository.UserMarkerRepository;
import com.example.collect_user_marker.service.UserMarkerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
    private ProblemTypeRepository problemTypeRepository;

    @Autowired
    private final FeignClientService feignClientService;

    private static final Logger logger = LoggerFactory.getLogger(UserMarkerServiceImpl.class);

    @Override
    @Transactional
    public UserMarkerEntity saveNewReport(UserMarkerDTO userMarkerDTO) {
        UserMarkerEntity userMarkerEntity = toEntity(userMarkerDTO);

        List<ImageDTO> photoIds = userMarkerEntity.getImages();
        Boolean analyseResult = false;

        for (ImageDTO photo : photoIds) {
            if (analyseResult)
                break;
            analyseResult = feignClientService.analyse(new PhotoDTO(photo.getFullImageId())).getIsHogweed();
        }

        userMarkerEntity.setPhotoVerification(analyseResult);

        logger.debug("Сохранена новая заявка: {}", userMarkerEntity);
        return userMarkerRepository.save(userMarkerEntity);
    }

    @Override
    public Page<UserMarkerEntity> getAllReports(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userMarkerRepository.findAll(pageable);
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
        report.setUpdateDate(Instant.now());

        report.setOperatorComment(operatorDetailsDTO.getOperatorComment() != null ? operatorDetailsDTO.getOperatorComment() : report.getOperatorComment());

        if (operatorDetailsDTO.getStatusCode() != null) {
            StatusEntity statusEntity = statusRepository.findByCode(operatorDetailsDTO.getStatusCode());
            if (statusEntity != null) {
                report.setStatus(statusEntity.getCode());
            }
            else {
                throw new StatusNotFoundException(operatorDetailsDTO.getStatusCode());
            }
        }

        report.setOperatorId(operatorDetailsDTO.getOperatorId() != null ? operatorDetailsDTO.getOperatorId() : report.getOperatorId());
        report.setOperatorName(operatorDetailsDTO.getOperatorId() != null ? "Иванов И.И." : report.getOperatorName());
        // TODO запрашивать имя оператора у Даши

        userMarkerRepository.save(report);
        logger.debug("Данные по заявке с айди {} успешно обновлены", id);

        return report;
    }

    private UserMarkerEntity toEntity(UserMarkerDTO dto) {
        UserMarkerEntity entity = new UserMarkerEntity();

        if (dto.getCoordinate() == null || dto.getCoordinate().size() != 2) {
            throw new IncorrectDataException("Dont have X, Y or both coordinates");
        }

        entity.setCoordinates(dto.getCoordinate());

        try {
            entity.setUserComment(dto.getDetails().getComment() != null ? dto.getDetails().getComment() : "");
            entity.setImages(dto.getDetails().getImages() != null ? dto.getDetails().getImages() : List.of());
            entity.setUserId(dto.getDetails().getUserId());

            ProblemTypeEntity problemTypeEntity = problemTypeRepository.findByCode(dto.getDetails().getProblemAreaType());
            if (problemTypeEntity != null) {
                entity.setProblemAreaType(problemTypeEntity.getCode());
            }
            else {
                throw new ProblemNotFoundException(dto.getDetails().getProblemAreaType());
            }
        }
        catch (NullPointerException e) {
            throw new IncorrectDataException(e.getMessage());
        }

        entity.setOperatorComment("");
        entity.setStatus(statusRepository.findDefaultStatus().getCode());

        entity.setOperatorId(null);
        entity.setOperatorName(null);

        entity.setCreateDate(Instant.now());
        entity.setUpdateDate(Instant.now());

        return entity;
    }
}
