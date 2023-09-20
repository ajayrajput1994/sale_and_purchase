package com.olxseller.olx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.olxseller.olx.model.WebSiteAddress;

@Repository
public interface WebSiteAddressRepository extends JpaRepository<WebSiteAddress,Integer> {

	@Query("select w from WebSiteAddress w ")
	public WebSiteAddress getSiteAddress();
}
