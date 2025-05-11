package com.olxseller.olx.helper;

import java.util.Random;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class UniqueIdGenerator { 
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 10; // Including the 'P'
    @Bean
    public String generateOrderId() {
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
        return "ORD"+uuid;
    }
    @Bean
    public  String generateProductId() {
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
        System.out.println("genratepod: "+uuid);
        return "P"+uuid;
    }

    @Bean
    public String generateUniqueProductCode() {
        // Start with 'P'
        StringBuilder productId = new StringBuilder("P");
        Random random = new Random();

        // Generate remaining characters
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            productId.append(CHARACTERS.charAt(index));
        }

        return productId.toString();
    }
}
