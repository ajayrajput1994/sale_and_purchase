package com.olxseller.olx.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
public class ContactUs {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int contactId;
	@NotNull
	//@notblank(message="Name con't be empty !!")
	private String name;
	@NotNull
	//@notblank(message="Email con't be empty !!")
	@Pattern(regexp="^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$",message="Invalid Email !!")
	private String email;
	@NotNull
	//@notblank(message="Subject con't be empty !!")
	private String subject;
	@NotNull
	//@notblank(message="Description con't be empty !!")
	private String Description;
	@NotNull
	private String Date;

	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public ContactUs() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ContactUs(int contactId, String name, String email, String subject, String description, String date) {
		super();
		this.contactId = contactId;
		this.name = name;
		this.email = email;
		this.subject = subject;
		Description = description;
		Date = date;
	}

	@Override
	public String toString() {
		return "ContactUs [contactId=" + contactId + ", name=" + name + ", email=" + email + ", subject=" + subject
				+ ", Description=" + Description + ", Date=" + Date + "]";
	}
	
	
}
