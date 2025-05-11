package com.olxseller.olx.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.olxseller.olx.DTO.CategoryDTO;

@Service
public interface CategoryService {
  CategoryDTO addCategory(CategoryDTO catDTO);
  CategoryDTO getCategory(int catId);
  List<CategoryDTO> getAllCategory();
  List<CategoryDTO> getAllCatsBySub(String sub);
  List<CategoryDTO> getAllCatsByMainCat(String mainCat);
  void deleteCategory(int catId);
}
