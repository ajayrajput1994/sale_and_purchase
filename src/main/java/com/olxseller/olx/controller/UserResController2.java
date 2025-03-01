package com.olxseller.olx.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olxseller.olx.DTO.UserDTO;
import com.olxseller.olx.DTO.UserPasswordDTO;
import com.olxseller.olx.service.UserDtoService;
import com.olxseller.olx.service.UserService;

@RestController
// @Validated
@RequestMapping("/user2")
public class UserResController2 {
  @Autowired
  private UserDtoService userService;

  @PostMapping
	public ResponseEntity<UserDTO> newUser(@Valid @RequestBody UserDTO userDTO){
    
    userDTO.setAgreed(true);
    userDTO.setEnabled(true);
    userDTO.setRole("ROLE_USER");
    userDTO.setImage("default.png"); 
    userDTO.setPasscode("12345"); 
    userDTO.setPasswordStr(userDTO.getPassword()); 
    userDTO.setOther_phone(userDTO.getPhone());
    userDTO.setWishList("[]");
		System.out.println("new user"+userDTO.toString());
		return ResponseEntity.ok(userService.newUser(userDTO));
	}

  @PutMapping("/{id}")
	public ResponseEntity<UserDTO> updatingUser2(@PathVariable int id,@RequestBody @Validated UserDTO userDTO){
		userDTO.setId(id);
		return ResponseEntity.ok(userService.updateUser2(userDTO));
	}

  @PutMapping("/password")
	public ResponseEntity<UserDTO> updatingUserPassword(@RequestBody @Validated UserPasswordDTO userDTO){
		// userDTO.setId(id);
		return ResponseEntity.ok(userService.updatePassword2(userDTO));
	}
  
  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> getByid(@PathVariable int id){
    return ResponseEntity.ok(userService.getUserByID2(id));
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable int id){
    userService.deleteUser2(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<List<UserDTO>> getAllUsers(){
    return ResponseEntity.ok(userService.Users());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(cv -> {
            String fieldName = ((PathImpl) cv.getPropertyPath()).getLeafNode().toString();
            String errorMessage = cv.getMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
