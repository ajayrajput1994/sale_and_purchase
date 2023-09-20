package com.olxseller.olx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.olxseller.olx.model.Banner;

@Repository
public interface BannerRepository extends JpaRepository<Banner,Integer> {
	@Query("select w from Banner w")
	public Banner getHomeBanner();
}
