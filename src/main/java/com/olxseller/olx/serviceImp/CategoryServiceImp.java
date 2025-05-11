package com.olxseller.olx.serviceImp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.olxseller.olx.DTO.CategoryDTO;
import com.olxseller.olx.model.Category;
import com.olxseller.olx.repository.CategoryRepository;
import com.olxseller.olx.service.CategoryService;

@Component
public class CategoryServiceImp implements CategoryService {

  @Autowired
  private CategoryRepository catRepo;

  @Override
  public CategoryDTO addCategory(CategoryDTO catDTO) {
    Optional<Category> existing=catRepo.findById(catDTO.getId());
    if(existing.isPresent()){
      Category cat=existing.get();
      BeanUtils.copyProperties(catDTO, cat, "id");
      return toDto(catRepo.save(cat));  
    }else{
      return toDto(catRepo.save(toEntity(catDTO)));
    }
  }

  @Override
  public CategoryDTO getCategory(int catId) {
    return toDto(catRepo.findById(catId).orElse(new Category()));
  }

  @Override
  public List<CategoryDTO> getAllCategory() {
    return catRepo.findAll().stream().map(this::toDto).toList();
  }

  @Override
  public List<CategoryDTO> getAllCatsBySub(String sub) {
    return catRepo.AllCategoryBySub(sub).stream().map(this::toDto).toList();
  }

  @Override
  public List<CategoryDTO> getAllCatsByMainCat(String mainCat) {
    return catRepo.AllCategoryByMain(mainCat).stream().map(this::toDto).toList();
  }

  @Override
  public void deleteCategory(int catId) {
    catRepo.deleteById(catId);
  }
  
  private CategoryDTO toDto(Category cat){
    CategoryDTO catDto=new CategoryDTO();
    catDto.setId(cat.getId());
    catDto.setTitle(cat.getTitle());
    catDto.setMainCategory(cat.getMainCategory());
    catDto.setSubCategory(cat.getSubCategory());
    catDto.setImage(cat.getImage());
    catDto.setPath(cat.getPath());
    return catDto;
  }

  private Category toEntity(CategoryDTO catDto){
    Category cat=new Category();
    cat.setId(catDto.getId());
    cat.setTitle(catDto.getTitle());
    cat.setMainCategory(catDto.getMainCategory());
    cat.setSubCategory(catDto.getSubCategory());
    cat.setImage(catDto.getImage());
    cat.setPath(catDto.getPath());
    return cat;
  }
}
