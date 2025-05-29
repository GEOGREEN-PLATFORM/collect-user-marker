package com.example.collect_user_marker.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JwtParserUtilTest {

    private JwtDecoder jwtDecoder;
    private JwtParserUtil jwtParserUtil;

    @BeforeEach
    void setUp() {
        jwtDecoder = mock(JwtDecoder.class);
        jwtParserUtil = new JwtParserUtil(jwtDecoder);
    }

    @Test
    @DisplayName("extractEmailFromJwt should return email if present")
    void extractEmailFromJwt_shouldReturnEmail() {
        Jwt mockJwt = mock(Jwt.class);
        when(jwtDecoder.decode("token")).thenReturn(mockJwt);
        when(mockJwt.getClaim("email")).thenReturn("user@example.com");

        String result = jwtParserUtil.extractEmailFromJwt("Bearer token");

        assertThat(result).isEqualTo("user@example.com");
    }

    @Test
    @DisplayName("extractEmailFromJwt should throw if email missing")
    void extractEmailFromJwt_shouldThrowIfMissing() {
        Jwt mockJwt = mock(Jwt.class);
        when(jwtDecoder.decode("token")).thenReturn(mockJwt);
        when(mockJwt.getClaim("email")).thenReturn(null);

        assertThatThrownBy(() -> jwtParserUtil.extractEmailFromJwt("Bearer token"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Некорректное значение поля в токене");
    }

    @Test
    @DisplayName("extractRoleFromJwt should return first role")
    void extractRoleFromJwt_shouldReturnRole() {
        Jwt mockJwt = mock(Jwt.class);
        when(jwtDecoder.decode("token")).thenReturn(mockJwt);

        Map<String, Object> userClient = Map.of("roles", List.of("operator", "user"));
        Map<String, Object> resourceAccess = Map.of("user-client", userClient);

        when(mockJwt.getClaim("resource_access")).thenReturn(resourceAccess);

        String result = jwtParserUtil.extractRoleFromJwt("Bearer token");

        assertThat(result).isEqualTo("operator");
    }
}
