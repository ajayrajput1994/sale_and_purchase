package com.olxseller.olx.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.olxseller.olx.model.City;

@Repository
public interface CityRepository extends JpaRepository<City,Integer> {
	
	@Query(value="select * from city",nativeQuery=true) 
	 public Page<City>  getAllCity(Pageable pageable);
	
	@Query(value="select * from city where state_name=:state",nativeQuery=true) 
	  public List<City> getAllCities(@Param("state") String state);
}
