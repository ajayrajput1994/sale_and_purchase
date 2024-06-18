package com.olxseller.olx.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class SubCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int subId;
	@NotNull
	//@notblank(message="Catalog Name con't be empty !!")
	private String subCatalog="";
	@NotNull
	private String mainCatalog="";
	@NotNull
	//@notblank(message="Title Name con't be empty !!")
	private String title="";
	@NotNull
	//@notblank(message="Keyword Name con't be empty !!")
	private String keyword="";
	@NotNull
	//@notblank(message="Description Name con't be empty !!")
	private String description="";
	@NotNull
	private String seoTitle="";
	@NotNull
	private String seoDescription="";
	@NotNull
	private String seoContent="";
	@NotNull
	private String image="";
	@NotNull
	private String path="";

	public int getSubId() {
		return subId;
	}

	public void setSubId(int subId) {
		this.subId = subId;
	}

	public String getSubCatalog() {
		return subCatalog;
	}

	public void setSubCatalog(String subCatalog) {
		this.subCatalog = subCatalog;
	}

	public String getMainCatalog() {
		return mainCatalog;
	}

	public void setMainCatalog(String mainCatalog) {
		this.mainCatalog = mainCatalog;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public SubCategory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubCategory(int subId, String subCatalog,String mainCatalog, String title, String keyword, String description, String seoTitle,
			String seoDescription, String seoContent, String image, String path) {
		super();
		this.subId = subId;
		this.subCatalog = subCatalog;
		this.mainCatalog=mainCatalog;
		this.title = title;
		this.keyword = keyword;
		this.description = description;
		this.seoTitle = seoTitle;
		this.seoDescription = seoDescription;
		this.seoContent = seoContent;
		this.image = image;
		this.path = path;
	}

	@Override
	public String toString() {
		return "SubCategory [subId=" + subId + ", subCatalog=" + subCatalog + ", mainCatalog=" + mainCatalog + ", title=" + title + ", keyword="
				+ keyword + ", description=" + description + ", seoTitle=" + seoTitle + ", seoDescription="
				+ seoDescription + ", seoContent=" + seoContent + ", image=" + image + ", path=" + path + "]";
	}
	
	
}
