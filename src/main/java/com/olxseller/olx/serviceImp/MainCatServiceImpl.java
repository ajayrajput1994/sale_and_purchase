package com.olxseller.olx.serviceImp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olxseller.olx.DTO.MainCatDTO;
import com.olxseller.olx.model.MainCategory;
import com.olxseller.olx.repository.MainCatRepository;
import com.olxseller.olx.service.MainCategoryService;

@Service
public class MainCatServiceImpl implements MainCategoryService {
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

  @Override
  public MainCatDTO addCategory(MainCatDTO categoryDTO) {
    Optional<MainCategory> exisCategory=CatRepo.findById(categoryDTO.getId());
    if(exisCategory.isPresent()){ 
      MainCategory category=exisCategory.get();
      BeanUtils.copyProperties(categoryDTO, category, "id"); 
      return toDTO(CatRepo.save(category));
    }else{ 
      return toDTO(CatRepo.save(toEntity(categoryDTO)));
    } 
  }

  @Override
  public MainCatDTO getCategory(int catId) {
    return toDTO(CatRepo.findById(catId).orElse(new MainCategory()));
  }

  @Override
  public MainCatDTO getCategoryByTitle(String title) {
    return toDTO(new MainCategory());
  }

  @Override
  public List<MainCatDTO> AllCategories() {
    return CatRepo.findAll().stream().map(this::toDTO).toList();  
  }
  
  private MainCatDTO toDTO(MainCategory category){
    MainCatDTO categoryDTO=new MainCatDTO();
    categoryDTO.setId(category.getMainId());
    categoryDTO.setTitle(category.getTitle());
    categoryDTO.setImage(category.getImage());
    categoryDTO.setPath(category.getPath());
    return categoryDTO;
  }

  private MainCategory toEntity(MainCatDTO categoryDTO){
    MainCategory category=new MainCategory();
    category.setMainId(categoryDTO.getId());
    category.setTitle(categoryDTO.getTitle());
    category.setImage(categoryDTO.getImage());
    category.setPath(categoryDTO.getPath());
    return category;
  }
}
