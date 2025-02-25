package com.olxseller.olx.service;

import java.util.List;

import com.olxseller.olx.DTO.UserDTO;
import com.olxseller.olx.DTO.UserPasswordDTO;

public interface UserDtoService {
  	
	UserDTO newUser(UserDTO userDTO); 
	List<UserDTO> Users();
	UserDTO getUserByID2(int userId);
	UserDTO findUserByEmail2(String email);
	UserDTO updateUser2(UserDTO userDTO);
	// UserDTO updatePassword2(UserDTO userDTO);
	UserDTO updatePassword2(UserPasswordDTO userDTO);
	void deleteUser2(int userId);
}
