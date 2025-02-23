package com.olxseller.olx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.olxseller.olx.model.Order;
@Repository
public interface OrderRepository extends JpaRepository<Order,Integer>{
  
	@Query("from Order as b where b.user.id=:userId")
	public List<Order> AllOrdersByUserID(@Param("userId") int userId);
}
