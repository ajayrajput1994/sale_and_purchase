package com.olxseller.olx.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.olxseller.olx.model.WebPage;
@Component
public interface WebPageService {
  
  WebPage creatWebPage(WebPage page);

  List<WebPage> getAllPages();
  WebPage getPageByName(String title);
  
  WebPage updateWebPage(WebPage page,int id);

  void deleteWebPage(int id);
}
