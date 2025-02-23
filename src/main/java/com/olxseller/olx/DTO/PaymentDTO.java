package com.olxseller.olx.DTO;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class PaymentDTO {
  private int id;

  @NotNull(message = "User ID cannot be null")
  @Min(value = 1,message="User ID must be greater than 0")
  private Integer userId;

  @NotNull(message = "Order ID cannot be null")
  @Min(value = 1,message="Order ID must be greater than 0")
  private Integer orderId;

  @NotNull(message = "Amount cannot be null")
  @Min(value = 1,message = "Amount must be greater than 0")
  private double amount;
  @NotBlank(message = "Payment method cannot be null")
  private String paymentMethod;
  
  @NotBlank(message = "Status cannot be null")
  private String status;

  private LocalDateTime paymentDate;
  private LocalDateTime updatedAt;

}
