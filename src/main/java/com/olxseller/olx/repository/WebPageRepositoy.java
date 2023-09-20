package com.olxseller.olx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.olxseller.olx.model.WebPage;

@Repository
public interface WebPageRepositoy extends JpaRepository<WebPage,Integer>{

	@Query("select w from WebPage w where w.name=:s")
	public WebPage getWebPageByName(@Param("s") String name);
	
}
