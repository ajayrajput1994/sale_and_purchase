package com.olxseller.olx.service;
import org.springframework.stereotype.Service;

import com.olxseller.olx.model.WebSiteAddress;

@Service
public interface WebAddressService{
  WebSiteAddress create(WebSiteAddress ob);
  WebSiteAddress getAddress();
  WebSiteAddress update(WebSiteAddress ob,int id);

}
