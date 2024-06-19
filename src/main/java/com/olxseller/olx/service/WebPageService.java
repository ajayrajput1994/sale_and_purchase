package com.olxseller.olx.service;

import org.springframework.stereotype.Component;

import com.olxseller.olx.model.WebPage;
@Component
public interface WebPageService {
  
  WebPage creatWebPage(WebPage page);
  
  WebPage updateWebPage(WebPage page,int id);

  void deleteWebPage(int id);
}
