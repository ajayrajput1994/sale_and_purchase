package com.olxseller.olx.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.olxseller.olx.model.UserAddress;

@Repository
public interface UserAddressRepo extends JpaRepository<UserAddress, Integer> {
  
}
