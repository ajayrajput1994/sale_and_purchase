package com.olxseller.olx.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olxseller.olx.model.ContactUs;
import com.olxseller.olx.repository.ContactUsRepository;
import com.olxseller.olx.service.ContactService;

@Service
public class ContactServiceImpl implements ContactService{
  @Autowired
  private ContactUsRepository contactRepo;
  @Override
  public ContactUs addContact(ContactUs obj) {
    return contactRepo.save(obj);
  }

  @Override
  public List<ContactUs> getAllContact() {
    return contactRepo.findAll();
  }

  @Override
  public ContactUs updateContact(ContactUs obj, int id) {
    ContactUs contact=contactRepo.findById(id).get();
    contact.setName(obj.getName());
    contact.setEmail(obj.getEmail());
    contact.setDescription(obj.getDescription());
    contact.setSubject(obj.getSubject());
    return contactRepo.save(contact);
  }

  @Override
  public void deleteContact(int id) {
    contactRepo.deleteById(id);  
  }
  
}
