package com.olxseller.olx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olxseller.olx.DTO.CartDTO;
import com.olxseller.olx.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

  @Autowired
  private CartService cartService;

  @PostMapping
  public ResponseEntity<CartDTO> addToCart(@RequestBody CartDTO cartDTO){
    return ResponseEntity.ok(cartService.addToCart(cartDTO));
  }

  @GetMapping("/{userId}")
  public ResponseEntity<CartDTO> getCartItems(@PathVariable int userId){

    return ResponseEntity.ok(cartService.getCartItems(userId));
  }
  
}
