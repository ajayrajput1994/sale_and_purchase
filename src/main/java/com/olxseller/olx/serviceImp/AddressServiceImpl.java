package com.olxseller.olx.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.olxseller.olx.repository.WebSiteAddressRepository;
import com.olxseller.olx.model.WebSiteAddress;
import com.olxseller.olx.service.GenricService;

public class AddressServiceImpl implements GenricService<WebSiteAddress> {

  @Autowired
  private WebSiteAddressRepository AddressRepo;
  @Override
  public WebSiteAddress create(WebSiteAddress ob) {
    return AddressRepo.save(ob);
  }

  @Override
  public WebSiteAddress getByID(int id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getByID'");
  }

  @Override
  public WebSiteAddress update(WebSiteAddress ob, int id) {
    ob.setId(id);
    return AddressRepo.save(ob);
  }

  @Override
  public void delete(int id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  @Override
  public List<WebSiteAddress> getAll() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getAll'");
  }
  
}
