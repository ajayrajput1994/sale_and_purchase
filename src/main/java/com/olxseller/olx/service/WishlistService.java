package com.olxseller.olx.service;

import java.util.List;

import com.olxseller.olx.DTO.WishlistDTO;


public interface WishlistService {
  
  WishlistDTO addToWishlist(WishlistDTO wishlistDTO); 

  void deleteWishlist(int id);

  List<WishlistDTO> getWishlist(int userId);
}
