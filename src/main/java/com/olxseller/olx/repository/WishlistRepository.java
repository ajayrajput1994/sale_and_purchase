package com.olxseller.olx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.olxseller.olx.model.Wishlist;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist,Integer> {
 
  @Query("From Wishlist as w where w.user.id=:userid")
  List<Wishlist> getWishlistByUserId(@Param("userid") int userid);
}
