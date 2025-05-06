package com.example.collect_user_marker.service.impl;

import com.example.collect_user_marker.consumer.dto.PhotoAnalyseRespDTO;
import com.example.collect_user_marker.entity.ProblemTypeEntity;
import com.example.collect_user_marker.entity.StatusEntity;
import com.example.collect_user_marker.entity.UserMarkerEntity;
import com.example.collect_user_marker.entity.spec.EntitySpecifications;
import com.example.collect_user_marker.exception.custom.IncorrectDataException;
import com.example.collect_user_marker.exception.custom.ProblemNotFoundException;
import com.example.collect_user_marker.exception.custom.ReportNotFoundException;
import com.example.collect_user_marker.exception.custom.StatusNotFoundException;
import com.example.collect_user_marker.feignClient.FeignClientPhotoAnalyseService;
import com.example.collect_user_marker.feignClient.FeignClientUserService;
import com.example.collect_user_marker.model.OperatorDetailsDTO;
import com.example.collect_user_marker.model.UserDTO;
import com.example.collect_user_marker.model.UserMarkerDTO;
import com.example.collect_user_marker.model.image.ImageDTO;
import com.example.collect_user_marker.producer.KafkaProducerService;
import com.example.collect_user_marker.producer.dto.PhotoAnalyseReqDTO;
import com.example.collect_user_marker.producer.dto.UpdateElementDTO;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

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
    private final FeignClientPhotoAnalyseService feignClientPhotoAnalyseService;

    @Autowired
    private final FeignClientUserService feignClientUserService;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    private static final Logger logger = LoggerFactory.getLogger(UserMarkerServiceImpl.class);

    @Override
    @Transactional
    public UserMarkerEntity saveNewReport(UserMarkerDTO userMarkerDTO, String token) {
        UserMarkerEntity userMarkerEntity = toEntity(userMarkerDTO);

        UserMarkerEntity result = userMarkerRepository.save(userMarkerEntity);
        logger.debug("Сохранена новая заявка: {}", userMarkerEntity);

        if (Objects.equals(result.getProblemAreaType(), "Борщевик"))
            sendPhotosToKafka(userMarkerEntity.getImages(), result.getId());

        return result;
    }

    @Override
    public Page<UserMarkerEntity> getAllReports(int page, int size, String problemType, Instant startDate,
                                                Instant endDate) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<UserMarkerEntity> spec = Specification.where(EntitySpecifications.hasFieldValue(problemType))
                .and(EntitySpecifications.hasDateBetween(startDate, endDate));
        return userMarkerRepository.findAll(spec, pageable);
    }

    @Override
    public UserMarkerEntity getReportById(UUID id) {
        return userMarkerRepository.findById(id).orElseThrow(
                () -> new ReportNotFoundException(id)
        );
    }

    @Override
    @Transactional
    public UserMarkerEntity updateReport(OperatorDetailsDTO operatorDetailsDTO, UUID id, String token) {
        UserMarkerEntity report = getReportById(id);
        report.setUpdateDate(Instant.now());

        report.setOperatorComment(operatorDetailsDTO.getOperatorComment() != null ? operatorDetailsDTO.getOperatorComment() : report.getOperatorComment());

        if (operatorDetailsDTO.getStatusCode() != null) {
            StatusEntity statusEntity = statusRepository.findByCode(operatorDetailsDTO.getStatusCode());
            if (statusEntity != null) {

                if (!Objects.equals(statusEntity.getCode(), report.getStatus())) {
                    kafkaProducerService.sendUpdate(new UpdateElementDTO(report.getId(), "USER_MARKER", statusEntity.getCode(), null));
                }

                report.setStatus(statusEntity.getCode());
            }
            else {
                throw new StatusNotFoundException(operatorDetailsDTO.getStatusCode());
            }
        }

        report.setOperator(getUserById(operatorDetailsDTO.getOperatorId(), token));


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

        entity.setOperator(null);

        entity.setCreateDate(Instant.now());
        entity.setUpdateDate(Instant.now());

        List<Integer> photoPredictions = new ArrayList<>(Arrays.asList(new Integer[dto.getDetails().getImages().size()]));
        Collections.fill(photoPredictions, -1);
        entity.setPhotoPredictions(photoPredictions);

        return entity;
    }

    private void sendPhotosToKafka(List<ImageDTO> photoIds, UUID userMarkerId) {
        if (photoIds.size() == 0) {
            return;
        }

        int counter = 0;
        for (ImageDTO photo : photoIds) {
            kafkaProducerService.sendPhoto(new PhotoAnalyseReqDTO(userMarkerId, counter, photo.getFullImageId()));
            logger.info("Фото {} успешно отправлено на анализ.", photo.getFullImageId());
            counter += 1;
        }
    }

    @Transactional
    public void updatePhotoVerification(PhotoAnalyseRespDTO photo) {
        UserMarkerEntity userMarker = userMarkerRepository.findById(photo.getUserMarkerId())
                .orElseThrow(() -> new IllegalStateException("UserMarker not found with id: " + photo.getUserMarkerId()));

        List<Integer> predictions = userMarker.getPhotoPredictions();
        predictions.set(photo.getProtoPosition(), photo.getPrediction());


        if (!predictions.contains(-1)) {
            long countGreaterThan50 = predictions.stream()
                    .filter(Objects::nonNull)
                    .filter(num -> num >= 65)
                    .count();
            userMarker.setPhotoVerification(countGreaterThan50 > predictions.size() / 2);
        }

        userMarker.setPhotoPredictions(predictions);

        userMarkerRepository.save(userMarker);

    }

    private UserDTO getUserById(UUID userId, String token) {
        if (userId == null) {
            return null;
        }
        return feignClientUserService.getUserById(token, userId);
    }
}
