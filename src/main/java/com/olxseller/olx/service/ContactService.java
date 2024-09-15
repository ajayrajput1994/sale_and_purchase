package com.olxseller.olx.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.olxseller.olx.model.ContactUs;

@Component
public interface ContactService {
  
  ContactUs addContact(ContactUs obj);

  List<ContactUs> getAllContact();

  ContactUs updateContact(ContactUs obj,int id);

  void deleteContact(int id);
}
