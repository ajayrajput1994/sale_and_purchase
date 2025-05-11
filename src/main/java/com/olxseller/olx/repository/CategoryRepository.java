package com.olxseller.olx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.olxseller.olx.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
  
  @Query("from Category as c where c.subCategory=:sub")
  List<Category> AllCategoryBySub(@Param("sub") String sub);
  
  @Query("from Category as c where c.mainCategory=:maincat")
  List<Category> AllCategoryByMain(@Param("maincat") String maincat);
}
