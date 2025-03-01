package com.olxseller.olx.serviceImp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olxseller.olx.DTO.CartDTO;
import com.olxseller.olx.model.Cart;
import com.olxseller.olx.model.User;
import com.olxseller.olx.repository.CartRepository;
import com.olxseller.olx.repository.UserRepository;
import com.olxseller.olx.service.CartService;
@Service
public class CartServiceImp implements CartService{
  @Autowired
  private CartRepository cartRepo;
  @Autowired
  private UserRepository userRepo;

  @Override
  public CartDTO addToCart(CartDTO cartDTO) {
    Cart existingCart=cartRepo.cartByUserId(cartDTO.getUserId());
    if(existingCart==null){
      return toDTO(cartRepo.save(toEntity(cartDTO)));
    }else{
      existingCart.setItems(cartDTO.getItems());
      return toDTO(cartRepo.save(existingCart));
    }
  }
  @Override
  public CartDTO getCartItems(int userId) {
    return toDTO(cartRepo.cartByUserId(userId));  
  }

  private Cart toEntity(CartDTO cartDTO){
    Cart cart=new Cart();
    cart.setId(cartDTO.getId());
    cart.setItems(cartDTO.getItems());
    User user=userRepo.findById(cartDTO.getUserId()).orElseThrow(()-> new RuntimeException("User not found"));
    cart.setUser(user);
    return cart;
  } 

  private CartDTO toDTO(Cart cart){
    CartDTO cartDTO=new CartDTO();
    cartDTO.setId(cart.getId());
    cartDTO.setItems(cart.getItems());
    cartDTO.setUserId(cart.getUser().getId());
    return cartDTO;
  }
  
}
