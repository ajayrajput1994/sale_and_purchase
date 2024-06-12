package com.olxseller.olx.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.olxseller.olx.model.MainCategory;
@Component
public interface CategoryService {
  
  MainCategory CreateMainCategory(MainCategory cat);

  List<MainCategory> getAllMainCategory();

  MainCategory getCategoryById(int id);
  
  void deleteCategoryById(int id);

  MainCategory getCategoryByName(String name);

  void updateCategory(MainCategory cat,int id);
}
