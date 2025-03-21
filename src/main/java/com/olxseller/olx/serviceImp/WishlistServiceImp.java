package com.olxseller.olx.serviceImp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olxseller.olx.DTO.WishlistDTO;
import com.olxseller.olx.model.User;
import com.olxseller.olx.model.Wishlist;
import com.olxseller.olx.repository.UserRepository;
import com.olxseller.olx.repository.WishlistRepository;
import com.olxseller.olx.service.WishlistService;
@Service
public class WishlistServiceImp implements WishlistService{

  @Autowired
  private WishlistRepository wishRepo;

  @Autowired
  private UserRepository userRepo;
  @Override
  public WishlistDTO addToWishlist(WishlistDTO wishlistDTO) {
    Wishlist existing=wishRepo.getWishlistByUserId(wishlistDTO.getUserId()); 
    if(existing!=null){ 
      existing.setCollection(wishlistDTO.getCollection());
      existing.setItems(wishlistDTO.getItems());
      return tDto(wishRepo.save(existing));
    }else{
      return tDto(wishRepo.save(toEntity(wishlistDTO)));
    }
  }


  @Override
  public void deleteWishlist(int id) {
    if(wishRepo.existsById(id)){
      wishRepo.deleteById(id);  
    }
  }

  @Override
  public WishlistDTO getWishlist(int userId) {
    return tDto(wishRepo.getWishlistByUserId(userId));
  }
  

  private WishlistDTO tDto(Wishlist wish){
    WishlistDTO wishDTO=new WishlistDTO();
    wishDTO.setId(wish.getId());
    wishDTO.setCollection(wish.getCollection());
    wishDTO.setItems(wish.getItems());
    wishDTO.setUserId(wish.getUser().getId());
    return wishDTO;
  }
  
  private Wishlist toEntity(WishlistDTO wishDTO){
    Wishlist wish=new Wishlist();
    wish.setId(wishDTO.getId());
    wish.setCollection(wishDTO.getCollection());
    wish.setItems(wishDTO.getItems());
    User user=userRepo.findById(wishDTO.getUserId()).orElseThrow(()-> new RuntimeException("User not found with ID: "+wishDTO.getUserId()));
    wish.setUser(user);
    return wish;
  }
  
}
