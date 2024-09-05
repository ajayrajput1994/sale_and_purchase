package com.olxseller.olx.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.olxseller.olx.model.City;

@Component
public interface CityService {
  List<City> getAllCity();

  City  createCity(City c);

  City updateCity(City c,int id);

  void deleteCity(int id);
  
}
