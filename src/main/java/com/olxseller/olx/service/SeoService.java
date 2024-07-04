package com.olxseller.olx.service;

import org.springframework.stereotype.Service;

import com.olxseller.olx.model.HomeSeo;
@Service
public interface SeoService {
  HomeSeo create(HomeSeo ob);
  HomeSeo update(HomeSeo ob,int id);
}
