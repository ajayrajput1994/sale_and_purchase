package com.olxseller.olx.service;

import com.olxseller.olx.DTO.CartDTO;

public interface CartService {
  CartDTO addToCart(CartDTO cartDTO); 
  CartDTO getCartItems(int userId);
}
