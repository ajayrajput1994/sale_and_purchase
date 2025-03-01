package com.olxseller.olx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olxseller.olx.DTO.WishlistDTO;
import com.olxseller.olx.service.WishlistService;

@RestController
@Validated
@RequestMapping("/wishlist")
public class WishlistController {

  @Autowired
  private WishlistService wishlistService;

  @PostMapping
  public ResponseEntity<WishlistDTO> addToWishlist(@Validated @RequestBody WishlistDTO wishlistDTO){

    return ResponseEntity.ok(wishlistService.addToWishlist(wishlistDTO));
  }
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteWishlist(@PathVariable int id){
    wishlistService.deleteWishlist(id);
  return  ResponseEntity.noContent().build();
  }

  @GetMapping("/{userid}")
  public ResponseEntity<List<WishlistDTO>> getWishList(@PathVariable int userid){

    return ResponseEntity.ok(wishlistService.getWishlist(userid));
  }


  
}
