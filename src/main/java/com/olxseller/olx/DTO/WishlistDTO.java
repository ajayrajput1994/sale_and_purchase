package com.olxseller.olx.DTO;
 
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter; 
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor; 

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WishlistDTO {

  private int id;

  @NotNull(message="User ID cannot be null")
  private int userId;

  @NotBlank(message="Collection Name can't ne blank")
  private String collection;

  private String items;

  
}
