package com.olxseller.olx.DTO;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
public class UserPasswordDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private int id;

  @NotNull(message = "Email can't be null")
  @Pattern(regexp="^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$",message="Invalid Email !!")
  private String email;
  
  @NotNull(message = "Password can't be null")
  @Size(min=4, max=15,message="Password must be between 4-10 characters !")
  private String password;
}




