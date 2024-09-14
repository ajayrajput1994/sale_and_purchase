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
	private String description;
	@NotNull
	private String postId;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	
	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public ContactToPublisher() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ContactToPublisher(int id, String name, String email, String phone, String description,
			String date, String postId) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.description = description;
		this.postId = postId;
		Date = date;
	}

	@Override
	public String toString() {
		return "ContactToPublisher [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone
				+ ", description=" + description + ", Date=" + Date + ", postId=" + postId + "]";
	}

	
}
