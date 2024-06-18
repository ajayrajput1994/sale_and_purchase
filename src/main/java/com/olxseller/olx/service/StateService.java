package com.olxseller.olx.service;

import org.springframework.stereotype.Component;

import com.olxseller.olx.model.RegionState;

@Component
public interface StateService {
  
  RegionState createState(RegionState st);

  RegionState updaateState(RegionState st,int id);

  void deleteState(int id);
}
