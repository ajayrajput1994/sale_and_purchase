package com.olxseller.olx.DTO;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
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
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    @NotNull(message = "Name can't be null")
		@Size(min=3, max=12,message="Name must be between 3-12 characters !")
    private String name;

    @NotNull(message = "Email can't be null")
		@Pattern(regexp="^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$",message="Invalid Email !!")
    private String email;

    @NotNull(message = "Phone can't be null")
		@Size(min=10, max=10,message="Phone must be between 10 digits !")
    private String phone;

    private String other_phone;

    @NotNull(message = "Password can't be null")
    private String password;

    private String passwordStr;

    private String image;

    private String role;

    private Boolean enabled;

    private Boolean agreed;

    private String create_at;

    private String update_at;

    private String passcode;

    private String wishList;
}
	
	
	
