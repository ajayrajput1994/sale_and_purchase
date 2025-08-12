package com.olxseller.olx.helper;

import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class UniqueIdGenerator {

  private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  private static final int CODE_LENGTH = 10;

  public String generateOrderId() {
    String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    return "ORD" + uuid;
  }

  public String generateProductId() {
    String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
    System.out.println("Generated Product ID: " + uuid);
    return "P" + uuid;
  }

  public String generateUniqueProductCode() {
    StringBuilder productId = new StringBuilder("P");
    Random random = new Random();

    for (int i = 0; i < CODE_LENGTH; i++) {
      int index = random.nextInt(CHARACTERS.length());
      productId.append(CHARACTERS.charAt(index));
    }

    return productId.toString();
  }
}
