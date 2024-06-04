package com.olxseller.olx.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class City {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cityId;
	@NotNull
	private String stateName;
	@NotNull
	//@notblank(message="City Name con't be empty !!")
	private String cityName;
	@NotNull
	//@notblank(message="Title con't be empty !!")
	private String title;
	//@notblank(message="keyword con't be empty !!")
	private String keyword;
	@NotNull
	//@notblank(message="Description con't be empty !!")
	private String description;
	@NotNull
	//@notblank(message="Seo Title con't be empty !!")
	private String seoTitle;
	@NotNull
	//@notblank(message="Seo Description con't be empty !!")
	private String seoDescription;
	@NotNull
	//@notblank(message="Seo Content con't be empty !!")
	private String seoContent;
	@NotNull
	private String path;
	@NotNull
	private String image;


	
	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}


	public City() {
		super();
		// TODO Auto-generated constructor stub
	}

	public City(int cityId,String stateName, String cityName, String title, String keyword, String description, String seoTitle,
			String seoDescription, String seoContent, String path, String image) {
		super();
		this.cityId = cityId;
		this.stateName=stateName;
		this.cityName = cityName;
		this.title = title;
		this.keyword = keyword;
		this.description = description;
		this.seoTitle = seoTitle;
		this.seoDescription = seoDescription;
		this.seoContent = seoContent;
		this.path = path;
		this.image = image;
	}

	@Override
	public String toString() {
		return "City [cityId=" + cityId + ", stateName=" + stateName + ", cityName=" + cityName + ", title=" + title + ", keyword=" + keyword
				+ ", description=" + description + ", seoTitle=" + seoTitle + ", seoDescription=" + seoDescription
				+ ", seoContent=" + seoContent + ", path=" + path + ", image=" + image + "]";
	}


	
}
