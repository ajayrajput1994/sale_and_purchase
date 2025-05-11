package com.olxseller.olx.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.olxseller.olx.helper.ResponseData;
import com.olxseller.olx.model.Comments;
import com.olxseller.olx.model.ContactToPublisher;
import com.olxseller.olx.model.ContactUs;
import com.olxseller.olx.repository.PublisherService;
import com.olxseller.olx.service.CommentService;
import com.olxseller.olx.service.ContactService;
import com.olxseller.olx.service.ProductService;

@RestController
public class FrontRestController {
  @Autowired
  public ResponseData responseData;
  @Autowired
  public CommentService commService;
  @Autowired
  public PublisherService pubService;
  @Autowired
  public ContactService contactService;
  @Autowired
  public ProductService productService;

  SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
	String date = sdf.format(new Date());

  @PostMapping("/comment/create")
	public ResponseEntity<?> createUpdateComment(@RequestBody Comments comm) {
		comm.setCreate_at(date);
    comm.setUpdate_at(date);
		System.out.println("Comments:" + comm);
		try {
			if (comm.getCommentId() > 0) {
				return new ResponseEntity<>(
						responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE",
								commService.updateComment(comm, comm.getCommentId())),
						HttpStatus.OK);
			}
			return new ResponseEntity<>(
					responseData.jsonSimpleResponse("SUCCESS", "Successfuly Created", "CREATE", commService.createComment(comm)),
					HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}

  @PostMapping("/publisher/create")
	public ResponseEntity<?> createUpdatePublisher(@RequestBody ContactToPublisher pub) {
		pub.setDate(date);
		System.out.println("Comments:" + pub);
		try {
			if (pub.getId() > 0) {
				return new ResponseEntity<>(
						responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE",
								pubService.updatePublisher(pub, pub.getId())),
						HttpStatus.OK);
			}
			return new ResponseEntity<>(
					responseData.jsonSimpleResponse("SUCCESS", "Successfuly Created", "CREATE", pubService.createPublisher(pub)),
					HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}

  @PostMapping("/contact/add")
	public ResponseEntity<?> AddContact(@RequestBody ContactUs contact) {
		contact.setDate(date);
		System.out.println("Comments:" + contact);
		try {
			if (contact.getContactId() > 0) {
				return new ResponseEntity<>(
						responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE",
						contactService.updateContact(contact, contact.getContactId())),
						HttpStatus.OK);
			}
			return new ResponseEntity<>(
					responseData.jsonSimpleResponse("SUCCESS", "Successfuly Created", "CREATE", contactService.addContact(contact)),
					HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}

  @GetMapping("/items/{Ids}")
	public ResponseEntity<?> getProductListByIds(@PathVariable("Ids") List<Integer> Ids ) { 
		System.out.println("Ids:" + Ids);
		try { 
				return new ResponseEntity<>(
						responseData.jsonSimpleResponse("SUCCESS", "Successfuly Loaded", "RETRIEVE",
						productService.getAllProductsByIds(Ids)),
						HttpStatus.OK); 
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}
}
