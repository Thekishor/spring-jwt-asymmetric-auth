package com.asymmetric_auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "audit_aware")
public class JpaConfig {

    @Bean("audit_aware")
    public AuditorAware<String> auditorAware() {
        return new ApplicationAuditorAware();
    }
}
