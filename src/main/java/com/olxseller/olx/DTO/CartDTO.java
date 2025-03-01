package com.olxseller.olx.DTO;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
  private int id;

  @NotNull(message="User ID cannot be null")
  private int userId;

  private String items;
}
