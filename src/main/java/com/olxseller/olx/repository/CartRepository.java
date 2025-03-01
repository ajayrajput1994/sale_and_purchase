package com.olxseller.olx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.olxseller.olx.model.Cart;
import com.olxseller.olx.model.Payment;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {
@Query("from Cart as b where b.user.id=:userId")
	public Cart cartByUserId(@Param("userId") int userId);
  
}
