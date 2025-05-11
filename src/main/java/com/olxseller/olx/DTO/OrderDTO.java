package com.olxseller.olx.DTO;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.olxseller.olx.helper.LocalDateTimeDeserializer;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDTO {
  private int id;

  @NotBlank(message = "order ID cannot be null")
  private String orderId;

  @NotNull(message = "User ID cannot be null")
  @Min(value = 1, message = "User ID must be greater than 0")
  private Integer userId;

  @NotBlank(message = "Customer Name cannot be null")
  private String customerName;

  @NotBlank(message = "items cannot be null")
  private String itemDta;

  @NotBlank(message = "items cannot be null")
  private String billing;

  @NotBlank(message = "items cannot be null")
  private String shipping;

  @NotBlank(message = "items cannot be null")
  private String vouchers;

  @NotNull(message = "GST cannot be null")
  @Min(value = 1, message = "GST must be greater than 0")
  private int gst;

  @NotNull(message = "Voucher Discount cannot ne null")
  // @Min(value = 1,message="User ID must be greater than 0")
  private double voucherDiscount;

  @NotNull(message = "Handling Fee cannot ne null")
  // @Min(value = 0,message="User ID must be greater than 0")
  private double handlingFee;

  @NotNull(message = "Processing cannot ne null")
  // @Min(value = 0,message="User ID must be greater than 0")
  private double processingFee;

  @NotNull(message = "Surge Fee cannot ne null")
  // @Min(value = 0,message="User ID must be greater than 0")
  private double surgeFee;

  @NotNull(message = "Delivery Fee cannot ne null")
  // @Min(value = 0,message="Delivery Fee must be greater than 0")
  private double deliveryFee;

  @NotNull(message = "Total Price cannot ne null")
  @Min(value = 1, message = "Total Price must be greater than 0")
  private double totalPrice;

  @NotNull(message = "Grand Total cannot ne null")
  @Min(value = 1, message = "Grand Total must be greater than 0")
  private double grandTotal;

  @NotBlank(message = "Status is mandatory")
  private String status;
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime orderDate;
  private LocalDateTime deliveredAt;
  private LocalDateTime updatedAt;
}