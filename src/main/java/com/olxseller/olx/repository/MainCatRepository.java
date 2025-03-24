package com.olxseller.olx.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.olxseller.olx.model.MainCategory;

@Repository
public interface MainCatRepository extends JpaRepository <MainCategory,Integer> {



	@Query(value="select * from main_category",nativeQuery=true) 
	 public Page<MainCategory>  getAllCatalogs(Pageable pageable);
	
	@Query(value="select * from main_category",nativeQuery=true) 
	  public List<MainCategory> getMainCatalogs();
	
}
