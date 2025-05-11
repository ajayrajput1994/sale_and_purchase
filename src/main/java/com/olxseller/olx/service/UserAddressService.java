package com.olxseller.olx.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.olxseller.olx.DTO.UserAddressDTO;
import com.olxseller.olx.model.UserAddress;

@Component
public interface UserAddressService {

  UserAddress AddAddress(UserAddress obj);

  UserAddress getAddress(int id);

  void setDefaultAddress(int id);

  UserAddress UpdateAddress(UserAddress obj, int id, int uid);

  void deleteAddress(int id, int uid);

  UserAddressDTO createAddress(UserAddressDTO obj);

  UserAddressDTO getAddressByid(int id);

  List<UserAddressDTO> getAllAddressByUserId(int uid);

}
