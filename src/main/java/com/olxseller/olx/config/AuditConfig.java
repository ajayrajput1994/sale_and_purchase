package com.olxseller.olx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.olxseller.olx.helper.AuditListener;
import com.olxseller.olx.repository.AuditTrailRepository;
@Configuration
public class AuditConfig {
  @Bean
    public AuditListener auditListener(AuditTrailRepository auditTrailRepository) {
        // AuditListener.setAuditTrailRepository(auditTrailRepository);
        return new AuditListener();
    }
}
