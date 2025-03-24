package com.olxseller.olx.DTO;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubCatDTO {
  private int id;

  @NotNull(message = "Sub Title missing.")
  private String title;
  
  @NotNull(message = "Category missing.")
  private String mainCatalog;
  
  private String image;

  private String path;

}
