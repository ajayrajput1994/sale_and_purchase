package com.olxseller.olx.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class HomeSeo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull
	private String title;
	@NotNull
	private String keyword;
	@NotNull
	private String description;
	@NotNull
	private String seoTitle;
	@NotNull
	private String seoDescription;
	@NotNull
	private String seoContent;

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

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSeoTitle() {
		return seoTitle;
	}

	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	public String getSeoDescription() {
		return seoDescription;
	}

	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	public String getSeoContent() {
		return seoContent;
	}

	public void setSeoContent(String seoContent) {
		this.seoContent = seoContent;
	}


	public HomeSeo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HomeSeo(int id, String title, String keyword, String description, String seoTitle, String seoDescription,
			String seoContent) {
		super();
		this.id = id;
		this.title = title;
		this.keyword = keyword;
		this.description = description;
		this.seoTitle = seoTitle;
		this.seoDescription = seoDescription;
		this.seoContent = seoContent;
	}

	@Override
	public String toString() {
		return "homeSeo [id=" + id + ", title=" + title + ", keyword=" + keyword + ", description=" + description
				+ ", seoTitle=" + seoTitle + ", seoDescription=" + seoDescription + ", seoContent=" + seoContent
				+ "]";
	}

	
	
}
