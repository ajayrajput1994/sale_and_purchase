package com.olxseller.olx.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.olxseller.olx.model.HomeSeo;
import com.olxseller.olx.repository.HomeSeoRepository;
import com.olxseller.olx.service.SeoService;

@Component
public class HomeServiceImple implements SeoService{
  @Autowired
  private HomeSeoRepository homeRepo;
  @Override
  public HomeSeo create(HomeSeo ob) {
    return homeRepo.save(ob);
  }

  @Override
  public HomeSeo update(HomeSeo ob, int id) {
   ob.setId(id);
   return homeRepo.save(ob);
  }
  
}
