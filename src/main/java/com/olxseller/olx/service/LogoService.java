package com.olxseller.olx.service;

import org.springframework.stereotype.Service;

import com.olxseller.olx.model.Banner;

@Service
public interface LogoService {
  Banner updateLogo(Banner ob,int id);
  Banner updateBanner(Banner ob, int id);
}
