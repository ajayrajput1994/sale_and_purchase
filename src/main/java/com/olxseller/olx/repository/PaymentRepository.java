package com.olxseller.olx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.olxseller.olx.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Integer> {
  
  @Query("from Payment as b where b.order.id=:orderId")
	public Payment paymentByOrderID(@Param("orderId") int orderId);
}
