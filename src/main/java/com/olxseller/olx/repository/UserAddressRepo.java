package com.olxseller.olx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.olxseller.olx.model.UserAddress;

@Repository
public interface UserAddressRepo extends JpaRepository<UserAddress, Integer> {

  @Query("from UserAddress as p where p.user.id=:userId")
  List<UserAddress> getAddressesByUserId(@Param("userId") int userId);
}
