package com.example.collect_user_marker.repository;

import com.example.collect_user_marker.entity.StatusEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = "spring.liquibase.enabled=false")
@ActiveProfiles("test")
class StatusRepositoryTest {

    @Autowired
    private StatusRepository repository;

    @Test
    @DisplayName("findByCode should return entity with expected code")
    void testFindByCode() {
        StatusEntity status = new StatusEntity();
        status.setCode("ACTIVE");
        status.setDescription("Активен");
        status.setDefault(false);

        repository.save(status);

        StatusEntity found = repository.findByCode("ACTIVE");

        assertThat(found).isNotNull();
        assertThat(found.getCode()).isEqualTo("ACTIVE");
        assertThat(found.getDescription()).isEqualTo("Активен");
    }

    @Test
    @DisplayName("findDefaultStatus should return entity where isDefault is true")
    void testFindDefaultStatus() {
        StatusEntity s1 = new StatusEntity();
        s1.setCode("TEMP");
        s1.setDefault(false);

        StatusEntity s2 = new StatusEntity();
        s2.setCode("DEFAULT");
        s2.setDefault(true);

        repository.save(s1);
        repository.save(s2);

        StatusEntity result = repository.findDefaultStatus();

        assertThat(result).isNotNull();
        assertThat(result.getCode()).isEqualTo("DEFAULT");
        assertThat(result.isDefault()).isTrue();
    }
}
