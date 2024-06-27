package com.olxseller.olx.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olxseller.olx.model.HomeSeo;
import com.olxseller.olx.repository.HomeSeoRepository;
import com.olxseller.olx.service.GenricService;

@Service
public class HomeServiceImple implements GenricService<HomeSeo>{
  @Autowired
  private HomeSeoRepository homeRepo;
  @Override
  public HomeSeo create(HomeSeo ob) {
    return homeRepo.save(ob);
  }

  @Override
  public HomeSeo getByID(int id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getByID'");
  }

  @Override
  public HomeSeo update(HomeSeo ob, int id) {
   ob.setId(id);
   return homeRepo.save(ob);
  }

  @Override
  public void delete(int id) {
    homeRepo.deleteById(id);
  }

  @Override
  public List<HomeSeo> getAll() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getAll'");
  }
  
}
