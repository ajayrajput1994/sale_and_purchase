package com.olxseller.olx.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olxseller.olx.model.ContactToPublisher;
import com.olxseller.olx.repository.ContactToPublisherRepository;
import com.olxseller.olx.repository.PublisherService;

@Service
public class PublisherServiceImpl  implements PublisherService{
  @Autowired
  private ContactToPublisherRepository pubRepo;

  @Override
  public ContactToPublisher createPublisher(ContactToPublisher obj) {
    return pubRepo.save(obj);
  }

  @Override
  public List<ContactToPublisher> getAllPublisher() {
    return pubRepo.findAll();
  }

  @Override
  public ContactToPublisher updatePublisher(ContactToPublisher obj, int id) {
    ContactToPublisher pub=pubRepo.findById(id).get();
    pub.setName(obj.getName());
    pub.setEmail(obj.getEmail());
    pub.setPhone(obj.getPhone());
    pub.setDescription(obj.getDescription());
    pub.setDate(obj.getDate());
    return pubRepo.save(pub);
  }

  @Override
  public void deletePublisher(int id) {
   pubRepo.deleteById(id);
  }
  
}
