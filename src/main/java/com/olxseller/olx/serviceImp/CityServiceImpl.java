package com.olxseller.olx.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olxseller.olx.model.City;
import com.olxseller.olx.repository.CityRepository;
import com.olxseller.olx.service.CityService;

@Service
public class CityServiceImpl implements CityService{
  @Autowired
  private CityRepository cityRepo;
  @Override
  public City createCity(City c) {
  c.setImage("default.png");
  c.setPath(c.getCityName());
  return cityRepo.save(c);
  }

  @Override
  public City updateCity(City c, int id) {
    c.setCityId(id);
    return cityRepo.save(c);
  }

  @Override
  public void deleteCity(int id) {
    cityRepo.deleteById(id);  
  }
  
}
