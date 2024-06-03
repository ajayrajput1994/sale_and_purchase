package com.olxseller.olx.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class ContactToPublisher {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotNull
	private String name;
	@NotNull
	private String email;
	@NotNull
	private String phone;
	@NotNull
	private String subject;
	@NotNull
	private String Description;
	@NotNull
	private String Date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public ContactToPublisher() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ContactToPublisher(int id, String name, String email, String phone, String subject, String description,
			String date) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.subject = subject;
		Description = description;
		Date = date;
	}

	@Override
	public String toString() {
		return "ContactToPublisher [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone
				+ ", subject=" + subject + ", Description=" + Description + ", Date=" + Date + "]";
	}

	
}
