package com.olxseller.olx.service;

import org.springframework.stereotype.Component;

import com.olxseller.olx.model.SubCategory;

@Component
public interface SubCategoryService {
  
  SubCategory  createSubCategory(SubCategory sub);

  SubCategory  updateSubCategory(SubCategory sub,int id);

  void deleteSubcategory(int id);
  
}
