package com.olxseller.olx.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olxseller.olx.model.SubCategory;
import com.olxseller.olx.repository.SubCatRepository;
import com.olxseller.olx.service.SubCategoryService;
@Service
public class SubcatServiceImpl implements SubCategoryService{
  @Autowired
  private SubCatRepository subRepo;
  @Override
  public SubCategory createSubCategory(SubCategory sub) {
    sub.setImage("default.png");
    sub.setPath(sub.getMainCatalog());
    return subRepo.save(sub);
  
  }
  @Override
  public void deleteSubcategory(int id) {
    subRepo.deleteById(id);
  }

  @Override
  public SubCategory updateSubCategory(SubCategory sub, int id) {
   sub.setSubId(id);
   return subRepo.save(sub);
  }
  @Override
  public List<SubCategory> getAllSubcat() {
    return subRepo.findAll();
  }
  
}
