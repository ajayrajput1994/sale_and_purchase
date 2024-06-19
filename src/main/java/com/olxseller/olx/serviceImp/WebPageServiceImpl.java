package com.olxseller.olx.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olxseller.olx.model.WebPage;
import com.olxseller.olx.repository.WebPageRepositoy;
import com.olxseller.olx.service.WebPageService;

@Service
public class WebPageServiceImpl implements WebPageService {
  @Autowired
  private WebPageRepositoy pageRepo;
  @Override
  public WebPage creatWebPage(WebPage page) {
    page.setImage("default.png");
    page.setPath(page.getName());
    return  pageRepo.save(page);
  }

  @Override
  public WebPage updateWebPage(WebPage page, int id) {
    page.setId(id);
    return pageRepo.save(page);
  }

  @Override
  public void deleteWebPage(int id) {
    pageRepo.deleteById(id);
  }
  
}
