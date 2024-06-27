package com.olxseller.olx.serviceImp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.olxseller.olx.model.WebSiteAddress;
import com.olxseller.olx.service.GenricService;

@Service
public class SocialServiceImpl implements GenricService<WebSiteAddress> {

  @Override
  public WebSiteAddress create(WebSiteAddress ob) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'create'");
  }

  @Override
  public WebSiteAddress getByID(int id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getByID'");
  }

  @Override
  public WebSiteAddress update(WebSiteAddress ob, int id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
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
