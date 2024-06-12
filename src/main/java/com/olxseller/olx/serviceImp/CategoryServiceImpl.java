package com.olxseller.olx.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olxseller.olx.model.MainCategory;
import com.olxseller.olx.repository.MainCatRepository;
import com.olxseller.olx.service.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {
  @Autowired
  private MainCatRepository CatRepo;
  @Override
  public MainCategory CreateMainCategory(MainCategory cat) {
    cat.setImage("default.png");
    cat.setPath(cat.getMainCatalog());
    return CatRepo.save(cat);
  }

  @Override
  public List<MainCategory> getAllMainCategory() {
    return CatRepo.findAll();
  }

  @Override
  public MainCategory getCategoryById(int id) {
    return CatRepo.findById(id).orElse(null);
  }

  @Override
  public MainCategory getCategoryByName(String name) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getCategoryByName'");
  }

  @Override
  public void updateCategory(MainCategory cat, int id) {
    cat.setMainId(id);
    CatRepo.save(cat);
  }

  @Override
  public void deleteCategoryById(int id) {
    CatRepo.deleteById(id);
  }
  
}
