package com.olxseller.olx.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

@Entity
public class Banner {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String title;

	private String banner;
	
	private String logo;
	@Transient
	private MultipartFile multipartfile;

	@Transient
	private MultipartFile multipartfile2;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public MultipartFile getMultipartfile() {
		return multipartfile;
	}

	public void setMultipartfile(MultipartFile multipartfile) {
		this.multipartfile = multipartfile;
	}

	public Banner() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Banner(int id, String title, String banner) {
		super();
		this.id = id;
		this.title = title;
		this.banner = banner;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public MultipartFile getMultipartfile2() {
		return multipartfile2;
	}

	public void setMultipartfile2(MultipartFile multipartfile2) {
		this.multipartfile2 = multipartfile2;
	}

	@Override
	public String toString() {
		return "Banner [id=" + id + ", title=" + title + ", banner=" + banner +", logo=" + logo + "]";
	}
	
	
}
