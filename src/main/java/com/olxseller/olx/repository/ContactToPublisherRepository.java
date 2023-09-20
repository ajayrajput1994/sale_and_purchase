package com.olxseller.olx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.olxseller.olx.model.ContactToPublisher;

@Repository
public interface ContactToPublisherRepository extends JpaRepository<ContactToPublisher,Integer> {

}
