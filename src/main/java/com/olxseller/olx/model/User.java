package com.olxseller.olx.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotNull
//@NotBlank(message="Name con't be empty !!")
	//@Size(min=3, max=12,message="Name must be between 3-12 characters !")
	private String name;
	@NotNull
	@Column(unique=true)
//@Pattern(regexp="^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$",message="Invalid Email !!")
	private String email;
	@NotNull
//@NotBlank(message="Phone con't be empty !!")
	//@Size(min=10, max=10,message="Phone must be between 10 digits !")
	private String phone;
	@NotNull
	private String other_phone;
	@NotNull
	//@NotBlank(message="Password can't be empty !")
	private String password;
	@NotNull
	private String image;
	@NotNull
	private String role;
	@NotNull
	private Boolean enabled;
	//@AssertTrue(message="Must agree term and conditions !!")
	private Boolean agreed;
	
	@NotNull
	private String create_at;
	
	@NotNull
	private String update_at;
	
	@NotNull
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="user")
	private List<Blog> blog=new ArrayList<>();

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

	public String getOther_phone() {
		return other_phone;
	}

	public void setOther_phone(String other_phone) {
		this.other_phone = other_phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getAgreed() {
		return agreed;
	}

	public void setAgreed(Boolean agreed) {
		this.agreed = agreed;
	}

	public List<Blog> getBlog() {
		return blog;
	}

	public void setBlog(List<Blog> blog) {
		this.blog = blog;
	}

	public User() {
		super(); 
	}

	public User(int id, String name,String email,
			String phone, String other_phone,String password,
			String image, String role, Boolean enabled, Boolean agreed,String create_at,String update_at, List<Blog> blog) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.other_phone = other_phone;
		this.password = password;
		this.image = image;
		this.role = role;
		this.enabled = enabled;
		this.agreed = agreed;
		this.create_at=create_at;
		this.update_at=update_at;
		this.blog = blog;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + ", other_phone="
				+ other_phone + ", password=" + password + ", image=" + image + ", role=" + role + ", enabled="
				+ enabled + ", agreed=" + agreed +", create_at=" + create_at +", update_at=" + update_at + "]";
	}

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




	
}
