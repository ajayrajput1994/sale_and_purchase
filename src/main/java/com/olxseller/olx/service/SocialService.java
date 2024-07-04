package com.olxseller.olx.service;

import org.springframework.stereotype.Service;

import com.olxseller.olx.model.WebSiteSocial;
@Service
public interface SocialService {
  WebSiteSocial create(WebSiteSocial ob);
  WebSiteSocial update(WebSiteSocial ob,int id);
}
