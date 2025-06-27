package com.olxseller.olx.DTO;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.olxseller.olx.helper.LocalDateTimeDeserializer;
import com.olxseller.olx.helper.LocalDateTimeSerializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuesAnsDTO {
  private int id;
  @NotNull(message = "User ID can't be null")
  private int userId;

  @NotNull(message = "Order ID can't be null")
  private int productId;

  @NotNull(message = "question be null")
  private String question;

  @NotBlank(message = "answer be null")
  private String answer;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime createdAt;
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime updatedAt;

}
