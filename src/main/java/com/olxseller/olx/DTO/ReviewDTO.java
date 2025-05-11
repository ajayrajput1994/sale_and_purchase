package com.olxseller.olx.DTO;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewDTO {
  private int id;
  @NotNull(message = "User ID can't be null")
  private int userId;

  @NotNull(message = "Order ID can't be null")
  private int productId;

  @NotNull(message = "rating can't be null")
  @Min(value = 1, message = "Rating must be greater than 0")
  private float rating;

  @NotBlank(message = "User ID can't be null")
  private String review;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

}
