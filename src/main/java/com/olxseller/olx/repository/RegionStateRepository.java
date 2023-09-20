package com.olxseller.olx.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.olxseller.olx.model.RegionState;

@Repository
public interface RegionStateRepository extends JpaRepository<RegionState,Integer> {
	
	@Query(value="select * from region_state",nativeQuery=true) 
	 public Page<RegionState>  getAllRegionState(Pageable pageable);

	@Query(value="select * from region_state",nativeQuery=true) 
	  public List<RegionState> getAllStates();
}
