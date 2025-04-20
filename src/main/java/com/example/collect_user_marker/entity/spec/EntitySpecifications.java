package com.example.collect_user_marker.entity.spec;

import com.example.collect_user_marker.entity.UserMarkerEntity;
import org.springframework.data.jpa.domain.Specification;
import java.time.Instant;

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
            return criteriaBuilder.between(root.get("createDate"), startDate, endDate);
        };
    }
}