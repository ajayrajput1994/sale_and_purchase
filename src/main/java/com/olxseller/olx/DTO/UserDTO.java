package com.olxseller.olx.DTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class UserDTO{
  private int id; 
	
  @NotBlank(message="Name con't be empty !!")
	@Size(min=3, max=12,message="Name must be between 3-12 characters !")
	private String name;

  @NotBlank(message="Email con't be empty !!")
  @Pattern(regexp="^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$",message="Invalid Email !!")
	private String email;

  @NotBlank(message="Phone con't be empty !!")
	@Size(min=10, max=10,message="Phone must be between 10 digits !")
	private String phone;

  // @NotBlank(message = "User ID cannot be null")
  private String other_phone;

  @NotBlank(message="Password can't be empty!")
	private String password="";
	
	
	
	private String passwordStr="";
	private Boolean enabled=true;
	
	//@AssertTrue(message="Must agree term and conditions !!")
	// private Boolean agreed=true;
	private String create_at;
	private String update_at;
	private String passcode="";
	private String wishList="[default]";
}
	
	
	
