package com.olxseller.olx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.olxseller.olx.model.Logo;

@Repository
public interface LogoRepository extends JpaRepository<Logo,Integer> {
	@Query("select w from Logo w ")
	public Logo getSiteLogo();
}
