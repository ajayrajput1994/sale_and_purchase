package com.olxseller.olx.DTO;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.olxseller.olx.helper.LocalDateTimeDeserializer;
import com.olxseller.olx.helper.LocalDateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
  private int id;

  @NotBlank(message = "Name is mandatory")
  private String name;

  @NotBlank(message = "Description is mandatory")
  @Size(max = 1000, message = "Description can be at most 1000 characters long")
  private String description;

  @NotNull(message = "Price is mandatory")
  @Min(value = 1, message = "Price must be greater than 0")
  private double price;
  @Builder.Default
  private double discount = 0.0;
  @Builder.Default
  private double offerAmount = 0.0;
  @Builder.Default
  private double taxRate = 18.0;
  @Builder.Default
  private double total = 0.0;// optional only for cart calculation

  @NotNull(message = "User ID cannot be null")
  @Min(value = 1, message = "User ID must be greater than 0")
  private Integer userId;

  // @NotNull(message = "quantity is mandatory")
  // @Min(value = 1, message = "Quantity must be greater than 0")
  @Builder.Default
  private int quantity = 1;

  @NotNull(message = "Stock is mandatory")
  @Min(value = 1, message = "Stock must be greater than 0")
  private int stock;

  @NotBlank(message = "Image is mandatory")
  private String image;

  @NotBlank(message = "Category is mandatory")
  private String category;

  @NotBlank(message = "Sub Category is mandatory")
  private String subCategory;

  @NotBlank(message = "Main Category is mandatory")
  private String mainCategory;

  private String code;
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime createdAt;
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime updatedAt;
}
