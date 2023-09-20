package com.olxseller.olx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.olxseller.olx.model.ContactUs;

@Repository
public interface ContactUsRepository  extends JpaRepository<ContactUs,Integer>{

}
