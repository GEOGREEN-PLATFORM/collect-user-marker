package com.example.collect_user_marker.entity.spec;

import com.example.collect_user_marker.entity.UserMarkerEntity;
import org.springframework.data.jpa.domain.Specification;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class EntitySpecifications {
    public static Specification<UserMarkerEntity> hasFieldValue(String fieldValue) {
        return (root, query, criteriaBuilder) -> {
            if (fieldValue == null || fieldValue.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("problemAreaType"), fieldValue);
        };
    }

    public static Specification<UserMarkerEntity> hasDateBetween(Instant startDate, Instant endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate == null || endDate == null) {
                return criteriaBuilder.conjunction();
            }

            Instant adjustedEndDate = endDate;
            if (startDate.equals(adjustedEndDate)) {
                adjustedEndDate = adjustedEndDate.plus(24, ChronoUnit.HOURS);
            }

            return criteriaBuilder.between(root.get("createDate"), startDate, adjustedEndDate);
        };
    }

    public static Specification<UserMarkerEntity> hasStatusValue(String fieldValue) {
        return (root, query, criteriaBuilder) -> {
            if (fieldValue == null || fieldValue.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("status"), fieldValue);
        };
    }
}