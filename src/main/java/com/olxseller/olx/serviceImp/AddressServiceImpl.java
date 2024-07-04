package com.olxseller.olx.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.olxseller.olx.model.WebSiteAddress;
import com.olxseller.olx.repository.WebSiteAddressRepository;
import com.olxseller.olx.service.WebAddressService;
@Component
public class AddressServiceImpl implements WebAddressService {

  @Autowired
  private WebSiteAddressRepository AddressRepo;
  @Override
  public WebSiteAddress create(WebSiteAddress ob) {
    System.out.println("address updating....");
    return AddressRepo.save(ob);
  }
  @Override
  public WebSiteAddress update(WebSiteAddress ob, int id) {
    ob.setId(id);
    System.out.println("updating"+ob);
    return AddressRepo.save(ob);
  }

}
