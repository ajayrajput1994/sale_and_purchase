package com.olxseller.olx.service;

import org.springframework.stereotype.Component;

import com.olxseller.olx.model.City;

@Component
public interface CityService {

  City  createCity(City c);

  City updateCity(City c,int id);

  void deleteCity(int id);
  
}
