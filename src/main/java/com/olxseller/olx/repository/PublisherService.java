package com.olxseller.olx.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import com.olxseller.olx.model.ContactToPublisher;

@Component
public interface PublisherService {
  
  ContactToPublisher createPublisher(ContactToPublisher obj);

  List<ContactToPublisher> getAllPublisher();

  ContactToPublisher updatePublisher(ContactToPublisher obj,int id);

  void deletePublisher(int id);
}
