package com.olxseller.olx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.olxseller.olx.model.WebSiteSocial;

@Repository
public interface WebSiteSocialRepository extends JpaRepository<WebSiteSocial,Integer> {
	
	@Query("select w from WebSiteSocial w")
	public WebSiteSocial getWebSocial();
}
