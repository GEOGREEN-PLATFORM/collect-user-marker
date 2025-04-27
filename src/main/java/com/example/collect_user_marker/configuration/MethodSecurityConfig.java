package com.example.collect_user_marker.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity(jsr250Enabled = true)
@Configuration
public class MethodSecurityConfig {
}
