package com.olxseller.olx.model;

import javax.persistence.Column;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

public class UserAndPost {

	private int id;
	
	private String mainCategory;
	
	private String category;
	
	private String Title;
	
	@Column(length=8000)
	private String Description;
	
	private double price;
	
	private String image;
	
	private String image2;
	
	private String image3;
	
	private String image4;
	
	private String image5;
	
	private String image6;
	
	private String region;
	
	
	private String regionState;

	
	private String city;
	
	private String address;
	
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
	
	private String name;
	
	private String email;
	
	private String  phone;
	
	private String other_number;

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

	public String getOther_number() {
		return other_number;
	}

	public void setOther_number(String other_number) {
		this.other_number = other_number;
	}

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
	

	
	@Override
	public String toString() {
		return "UserAndPost [id=" + id + ", mainCategory=" + mainCategory + ", category=" + category + ", Title="
				+ Title + ", Description=" + Description + ", price=" + price + ", image=" + image + ", image2="
				+ image2 + ", image3=" + image3 + ", image4=" + image4 + ", image5=" + image5 + ", image6=" + image6
				+ ", region=" + region + ", regionState=" + regionState + ", city=" + city + ", address=" + address
				+ ", name=" + name + ", email=" + email + ", phone=" + phone + ", other_number=" + other_number + "]";
	}
}
