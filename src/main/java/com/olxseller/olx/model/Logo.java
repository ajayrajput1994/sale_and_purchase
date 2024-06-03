package com.olxseller.olx.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

@Entity
public class Logo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull
	private String logo;

	@Transient
	private MultipartFile multipartfile;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public MultipartFile getMultipartfile() {
		return multipartfile;
	}

	public void setMultipartfile(MultipartFile multipartfile) {
		this.multipartfile = multipartfile;
	}

	public Logo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Logo(int id, String logo) {
		super();
		this.id = id;
		this.logo = logo;
	}

	@Override
	public String toString() {
		return "Logo [id=" + id + ", logo=" + logo + "]";
	}
	
	
}
