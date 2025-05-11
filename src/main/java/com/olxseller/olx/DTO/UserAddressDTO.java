package com.olxseller.olx.DTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserAddressDTO {
  private int id;

  @NotNull(message = "User ID cannot be null")
  @Min(value = 1, message = "User ID must be greater than 0")
  private Integer userId;

  @NotBlank(message = "Name is mandatory")
  private String name;
  @NotBlank(message = "City is mandatory")
  private String city;
  @NotBlank(message = "State is mandatory")
  private String state;
  @NotBlank(message = "Region is mandatory")
  private String region;
  @NotBlank(message = "Phone is mandatory")
  private String phone;
  @NotBlank(message = "Pincode is mandatory")
  private String pin_code;
  @NotBlank(message = "Address is mandatory")
  private String address;
  @NotBlank(message = "Landmark is mandatory")
  private String landmark;

  private String other_phone;
  private String active;
  private String address_type;
}
