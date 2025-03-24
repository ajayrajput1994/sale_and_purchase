package com.olxseller.olx.serviceImp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olxseller.olx.DTO.SubCatDTO;
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
  @Override
  public SubCatDTO addSubCategory(SubCatDTO subCatDTO) {
    System.out.println("update to: "+subCatDTO);
    Optional<SubCategory> existing=subRepo.findById(subCatDTO.getId());
    if(existing.isPresent()){ 
      SubCategory subcat=existing.get();
      System.out.println("exist1: "+subcat);
      BeanUtils.copyProperties(subCatDTO, subcat, "id");
      System.out.println("exist2: "+subcat);
      return toDTO(subRepo.save(subcat));
    }else{
      System.out.println("new: "+subCatDTO);
      return toDTO(subRepo.save(toEntity(subCatDTO)));
    }
  }
  @Override
  public List<SubCatDTO> allSubcats() {
    return subRepo.findAll().stream().map(this::toDTO).toList();  
  }
  @Override
  public SubCatDTO getSubCategory(int subId) {
    return toDTO(subRepo.findById(subId).orElse(new SubCategory()));  
  }
  @Override
  public List<SubCatDTO> allSubcatsByCategory(String cat) {
   return subRepo.getAllSubCatalogs(cat).stream().map(this::toDTO).toList();  
  }
  @Override
  public void deleteSubcat(int id) {
     subRepo.deleteById(id);
  }
 
  private SubCatDTO toDTO(SubCategory subcat){
    SubCatDTO subDto=new SubCatDTO();
    subDto.setId(subcat.getSubId());
    subDto.setMainCatalog(subcat.getMainCatalog());
    subDto.setTitle(subcat.getTitle());
    subDto.setImage(subcat.getImage());
    subDto.setPath(subcat.getPath());
    return subDto;
  }

  private SubCategory toEntity(SubCatDTO subDto){
    SubCategory subcat=new SubCategory();
    subcat.setSubId(subDto.getId());
    subcat.setTitle(subDto.getTitle());
    subcat.setMainCatalog(subDto.getMainCatalog());
    subcat.setImage(subDto.getImage());
    subcat.setPath(subDto.getPath());
    return subcat;
  }
}
