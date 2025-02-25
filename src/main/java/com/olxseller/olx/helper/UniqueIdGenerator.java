package com.olxseller.olx.helper;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class UniqueIdGenerator { 
    @Bean
    public String generateOrderId() {
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
        return "ORD"+uuid;
    }
    @Bean
    public  String generateProductId() {
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
        return "P"+uuid;
    }
}
