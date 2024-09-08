package com.olxseller.olx.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Blog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotNull
	private String mainCategory="";
	@NotNull
	private String category="";
	@NotNull
	private String Title="";
	@NotNull
	@Column(length=8000)
	private String Description="";
	@NotNull
	private double price;
	@NotNull
	private String image="";
	@NotNull
	private String image2="";
	@NotNull
	private String image3="";
	@NotNull
	private String image4="";
	@NotNull
	private String image5="";

	@NotNull
	private String image6="";

	@NotNull
	private String region="";
	
	@NotNull
	private String regionState="";

	@NotNull
	private String city="";
	@NotNull
	private String address="";

	// seller info
	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private User user;
	
	@Transient
	private MultipartFile multipartFile;
	
	@Transient
	private MultipartFile multipartFile1;
	
	@Transient
	private MultipartFile multipartFile2;
	
	@Transient
	private MultipartFile multipartFile3;
	
	@Transient
	private MultipartFile multipartFile4;
	
	@Transient
	private MultipartFile multipartFile5;
	
	@NotNull
	private String create_at;
	
	@NotNull
	private String update_at;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMainCategory() {
		return mainCategory;
	}

	public void setMainCategory(String mainCategory) {
		this.mainCategory = mainCategory;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

	public String getImage3() {
		return image3;
	}

	public void setImage3(String image3) {
		this.image3 = image3;
	}

	public String getImage4() {
		return image4;
	}

	public void setImage4(String image4) {
		this.image4 = image4;
	}

	public String getImage5() {
		return image5;
	}

	public void setImage5(String image5) {
		this.image5 = image5;
	}

	public String getImage6() {
		return image6;
	}

	public void setImage6(String image6) {
		this.image6 = image6;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRegionState() {
		return regionState;
	}

	public void setRegionState(String regionState) {
		this.regionState = regionState;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/*
	 * public Comments getComments() { return comments; }
	 * 
	 * public void setComments(Comments comments) { this.comments = comments; }
	 */
	
	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

	public MultipartFile getMultipartFile1() {
		return multipartFile1;
	}

	public void setMultipartFile1(MultipartFile multipartFile1) {
		this.multipartFile1 = multipartFile1;
	}

	public MultipartFile getMultipartFile2() {
		return multipartFile2;
	}

	public void setMultipartFile2(MultipartFile multipartFile2) {
		this.multipartFile2 = multipartFile2;
	}

	public MultipartFile getMultipartFile3() {
		return multipartFile3;
	}

	public void setMultipartFile3(MultipartFile multipartFile3) {
		this.multipartFile3 = multipartFile3;
	}

	public MultipartFile getMultipartFile4() {
		return multipartFile4;
	}

	public void setMultipartFile4(MultipartFile multipartFile4) {
		this.multipartFile4 = multipartFile4;
	}

	public MultipartFile getMultipartFile5() {
		return multipartFile5;
	}

	public void setMultipartFile5(MultipartFile multipartFile5) {
		this.multipartFile5 = multipartFile5;
	}


	
	
	
	public Blog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Blog(int id,String mainCategory, String category, String title,
		 String description, double price, String image, String image2,
			String image3, String image4, String image5, String image6,
			String region, String regionState, String city, String address,
			User user, String create_at, String update_at) {
		super();
		this.id = id;
		this.mainCategory = mainCategory;
		this.category = category;
		Title = title;
		Description = description;
		this.price = price;
		this.image = image;
		this.image2 = image2;
		this.image3 = image3;
		this.image4 = image4;
		this.image5 = image5;
		this.image6 = image6;
		this.region = region;
		this.regionState = regionState;
		this.city = city;
		this.address = address;
		this.user = user;
		this.create_at = create_at;
		this.update_at = update_at;
	}

	// @Override
	// public String toString() {
	// 	return "Blog [id=" + id + ", mainCategory=" + mainCategory + ", category=" + category + ", Title=" + Title
	// 			+ ", Description=" + Description + ", price=" + price + ", image=" + image + ", image2=" + image2
	// 			+ ", image3=" + image3 + ", image4=" + image4 + ", image5=" + image5 + ", image6=" + image6
	// 			+ ", region=" + region + ", regionState=" + regionState + ", city=" + city + ", address=" + address
	// 			+ ", create_at=" + create_at + ", update_at=" + update_at + "]";
	// }

	public String getCreate_at() {
		return create_at;
	}

	public void setCreate_at(String create_at) {
		this.create_at = create_at;
	}

	public String getUpdate_at() {
		return update_at;
	}

	public void setUpdate_at(String update_at) {
		this.update_at = update_at;
	}


	@Override
	public String toString() {
		return "Blog [id=" + id + ", mainCategory=" + mainCategory + ", category=" + category + ", Title=" + Title
				+ ", Description=" + Description + ", price=" + price + ", image=" + image + ", image2=" + image2 + ", image3="
				+ image3 + ", image4=" + image4 + ", image5=" + image5 + ", image6=" + image6 + ", region=" + region
				+ ", regionState=" + regionState + ", city=" + city + ", address=" + address + ", user=" + user + ", create_at="
				+ create_at + ", update_at=" + update_at + "]";
	}
	
}
