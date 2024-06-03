package com.olxseller.olx.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class RegionState {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int stateId;
	@NotNull
	private String region;
	@NotNull
	@NotBlank(message="State Name con't be empty !!")
	private String stateName;
	@NotNull
	@NotBlank(message="Title con't be empty !!")
	private String title;
	@NotNull
	@NotBlank(message="Keyword con't be empty !!")
	private String keyword;
	@NotNull
	@NotBlank(message="Description con't be empty !!")
	private String description;
	@NotNull
	@NotBlank(message="Seo Title con't be empty !!")
	private String seoTitle;
	@NotNull
	@NotBlank(message="Seo Description con't be empty !!")
	private String seoDescription;
	@NotNull
	@NotBlank(message="Seo Content con't be empty !!")
	private String seoContent;
	@NotNull
	private String path;
	@NotNull
	private String image;

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	
	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
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

	public RegionState() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RegionState(int stateId,String region, String stateName, String title, String keyword, String description, String seoTitle,
			String seoDescription, String seoContent, String path, String image) {
		super();
		this.stateId = stateId;
		this.region=region;
		this.stateName = stateName;
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
		return "RegionState [stateId=" + stateId + ", region=" + region +", stateName=" + stateName + ", title=" + title + ", keyword="
				+ keyword + ", description=" + description + ", seoTitle=" + seoTitle + ", seoDescription="
				+ seoDescription + ", seoContent=" + seoContent + ", path=" + path + ", image=" + image + "]";
	}
	
	
	
}
