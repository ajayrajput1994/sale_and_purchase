package com.olxseller.olx.DTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDTO {
  private long id;

  @NotNull(message = "User ID cannot be null")
  @Min(value = 1,message="User ID must be greater than 0")
  private Integer userId;

  @NotBlank(message = "Name cannot be null")
  private String userName;

  @NotBlank(message = "Product json cannot be null")
  private String productDta;

  @NotNull(message = "Quanity cannot be null")
  @Min(value = 1,message="Quantity must be greater than 0")
  private int quantity;

  @NotNull(message = "Total cannot ne null")
  @Min(value = 1,message="User ID must be greater than 0")
  private double totalPrice;

  @NotBlank(message = "Status is mandatory")
  private String status;

}
