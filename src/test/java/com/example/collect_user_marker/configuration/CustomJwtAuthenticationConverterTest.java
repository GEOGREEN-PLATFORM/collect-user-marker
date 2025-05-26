package com.example.collect_user_marker.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class CustomJwtAuthenticationConverterTest {

    private CustomJwtAuthenticationConverter converter;

    @BeforeEach
    void setUp() {
        converter = new CustomJwtAuthenticationConverter();
    }

    @Test
    @DisplayName("Должен вернуть роли из resource_access.user-client.roles")
    void extractAuthorities_shouldReturnCustomRoles() {
        Map<String, Object> userClient = Map.of("roles", List.of("operator", "admin"));
        Map<String, Object> resourceAccess = Map.of("user-client", userClient);
        Map<String, Object> claims = Map.of("resource_access", resourceAccess);

        Jwt jwt = new Jwt(
                "token",
                Instant.now(),
                Instant.now().plusSeconds(3600),
                Map.of("alg", "none"),
                claims
        );

        var result = converter.convert(jwt);
        assertThat(result).isNotNull();
        assertThat(result.getAuthorities())
                .extracting(GrantedAuthority::getAuthority)
                .contains("ROLE_OPERATOR", "ROLE_ADMIN");
    }

    @Test
    @DisplayName("Не должен упасть, если user-client есть, но roles отсутствует")
    void extractAuthorities_shouldNotFailWhenRolesIsMissing() {
        // user-client присутствует, но без roles
        Map<String, Object> userClient = Map.of(); // пустой
        Map<String, Object> resourceAccess = Map.of("user-client", userClient);
        Map<String, Object> claims = Map.of("resource_access", resourceAccess);

        Jwt jwt = new Jwt(
                "token",
                Instant.now(),
                Instant.now().plusSeconds(3600),
                Map.of("alg", "none"),
                claims
        );

        var result = converter.convert(jwt);
        assertThat(result).isNotNull();
        assertThat(result.getAuthorities()).isEmpty();
    }

    @Test
    @DisplayName("Не должен упасть при отсутствии resource_access, user-client и roles")
    void extractAuthorities_shouldNotFailWhenAllMissing() {
        // создаём вручную карту, позволяющую null
        Map<String, Object> userClient = new HashMap<>();
        userClient.put("roles", null);

        Map<String, Object> resourceAccess = new HashMap<>();
        resourceAccess.put("user-client", userClient);

        Map<String, Object> claims = new HashMap<>();
        claims.put("resource_access", resourceAccess);

        Jwt jwt = new Jwt(
                "token",
                Instant.now(),
                Instant.now().plusSeconds(3600),
                Map.of("alg", "none"),
                claims
        );

        var result = converter.convert(jwt);
        assertThat(result).isNotNull();
        assertThat(result.getAuthorities()).isEmpty();
    }

}
