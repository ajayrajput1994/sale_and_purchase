package com.olxseller.olx.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.olxseller.olx.DTO.SubCatDTO;
import com.olxseller.olx.model.SubCategory;

@Component
public interface SubCategoryService {

List<SubCategory> getAllSubcat();

  SubCategory  createSubCategory(SubCategory sub);

  SubCategory  updateSubCategory(SubCategory sub,int id);

  void deleteSubcategory(int id);
  

  SubCatDTO addSubCategory(SubCatDTO subCatDTO);
  List<SubCatDTO> allSubcats();
  SubCatDTO getSubCategory(int subId);
  List<SubCatDTO> allSubcatsByCategory(String cat);
  void deleteSubcat(int id);
}
