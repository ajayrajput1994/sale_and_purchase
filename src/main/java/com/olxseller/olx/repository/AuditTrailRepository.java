package com.olxseller.olx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.olxseller.olx.model.AuditTrail;

@Repository
public interface AuditTrailRepository extends JpaRepository<AuditTrail,Integer> {
  
}
