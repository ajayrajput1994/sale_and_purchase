package com.olxseller.olx.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.olxseller.olx.model.WebSiteSocial;
import com.olxseller.olx.repository.WebSiteSocialRepository;
import com.olxseller.olx.service.SocialService;

@Component
public class SocialServiceImpl implements SocialService {

  @Autowired
  private WebSiteSocialRepository socialRepo;
  @Override
  public WebSiteSocial create(WebSiteSocial ob) {
    if(socialRepo.findAll().isEmpty()){
      ob=socialRepo.save(ob);
    }
    return  ob;
  }

  @Override
  public WebSiteSocial update(WebSiteSocial ob, int id) {
    ob.setId(id);
    return socialRepo.save(ob);
  }

  @Override
  public WebSiteSocial getSocialLinks() {
  return  socialRepo.findAll().get(0);
  }
 
  
}
