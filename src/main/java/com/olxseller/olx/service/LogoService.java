package com.olxseller.olx.service;

import org.springframework.stereotype.Service;

import com.olxseller.olx.model.Banner;
import com.olxseller.olx.model.Logo;

@Service
public interface LogoService {
  Logo updateLogo(Logo ob,int id);
  Banner updateBanner(Banner ob, int id);
}
