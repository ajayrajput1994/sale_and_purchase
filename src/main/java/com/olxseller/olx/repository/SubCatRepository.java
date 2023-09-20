package com.olxseller.olx.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.olxseller.olx.model.SubCategory;

@Repository
public interface SubCatRepository extends JpaRepository<SubCategory,Integer> {
	

	@Query(value="select * from sub_category",nativeQuery=true) 
	 public Page<SubCategory>  getAllSubCatalogs(Pageable pageable);

	@Query(value="select * from sub_category where main_catalog=:cat",nativeQuery=true) 
	  public List<SubCategory> getAllSubCatalogs(@Param("cat") String cat);
}
