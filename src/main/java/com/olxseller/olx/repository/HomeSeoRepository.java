package com.olxseller.olx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.olxseller.olx.model.HomeSeo;

@Repository
public interface HomeSeoRepository extends JpaRepository<HomeSeo,Integer>{
	
	@Query("select h from HomeSeo h")
	public HomeSeo getHomeSeo();

}
