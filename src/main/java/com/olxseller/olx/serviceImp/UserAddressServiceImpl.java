package com.olxseller.olx.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olxseller.olx.model.UserAddress;
import com.olxseller.olx.repository.UserAddressRepo;
import com.olxseller.olx.service.UserAddressService;
@Service
public class UserAddressServiceImpl implements UserAddressService {
  @Autowired
  private UserAddressRepo repo;
  @Override
  public UserAddress AddAddress(UserAddress obj) {
    return repo.save(obj);
  }

  @Override
  public UserAddress getAddress(int id) {
    return repo.findById(id).get();
  }

  @Override
  public UserAddress UpdateAddress(UserAddress obj, int id, int uid) {
    obj.setId(id);
    return repo.save(obj);
  }

  @Override
  public List<UserAddress> getAddressList(int uid) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getAddressList'");
  }

  @Override
  public void deleteAddress(int id, int uid) {
   UserAddress address=repo.findById(id).get();
   address.setUser(null);
   repo.delete(address);
  }
  
}
