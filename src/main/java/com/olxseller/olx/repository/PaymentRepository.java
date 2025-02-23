package com.olxseller.olx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.olxseller.olx.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Integer> {
  
}
