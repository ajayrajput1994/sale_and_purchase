package com.olxseller.olx.DTO;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryDTO {
  
  private int id;
  @NotNull(message = "Title Missing.")
  private String title;
  @NotNull(message = "Main Category Missing.")
  private String mainCategory;
  @NotNull(message = "Sub Category Missing.")
  private String subCategory;
  private String image;
  private String path;
}
