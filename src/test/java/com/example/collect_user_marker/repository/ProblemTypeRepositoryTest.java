package com.example.collect_user_marker.repository;

import com.example.collect_user_marker.entity.ProblemTypeEntity;
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
class ProblemTypeRepositoryTest {

    @Autowired
    private ProblemTypeRepository repository;

    @Test
    @DisplayName("findByCode should return entity with expected code")
    void testFindByCode() {
        ProblemTypeEntity problem = new ProblemTypeEntity();
        problem.setCode("CODE_123");
        problem.setDescription("Test problem");
        problem.setDefault(false);

        repository.save(problem);

        ProblemTypeEntity found = repository.findByCode("CODE_123");

        assertThat(found).isNotNull();
        assertThat(found.getCode()).isEqualTo("CODE_123");
    }

    @Test
    @DisplayName("findDefaultProblem should return entity with isDefault = true")
    void testFindDefaultProblem() {
        ProblemTypeEntity nonDefault = new ProblemTypeEntity();
        nonDefault.setCode("NOT_DEFAULT");
        nonDefault.setDefault(false);

        ProblemTypeEntity def = new ProblemTypeEntity();
        def.setCode("DEFAULT");
        def.setDefault(true);

        repository.save(nonDefault);
        repository.save(def);

        ProblemTypeEntity result = repository.findDefaultProblem();

        assertThat(result).isNotNull();
        assertThat(result.getCode()).isEqualTo("DEFAULT");
        assertThat(result.isDefault()).isTrue();
    }
}
