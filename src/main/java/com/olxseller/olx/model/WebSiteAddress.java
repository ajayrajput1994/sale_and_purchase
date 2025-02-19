package com.olxseller.olx.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

@Entity
public class WebSiteAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String editorEmail="";
	
	private String infoEmail="";
	
	private String adminEmail="";
	@Pattern(regexp = "\\d{10}")
	private String phone="";
	@Pattern(regexp = "\\d{10}")
	private String phone2="";
	
	private String Address="";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEditorEmail() {
		return editorEmail;
	}

	public void setEditorEmail(String editorEmail) {
		this.editorEmail = editorEmail;
	}

	public String getInfoEmail() {
		return infoEmail;
	}

	public void setInfoEmail(String infoEmail) {
		this.infoEmail = infoEmail;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}



	public WebSiteAddress() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WebSiteAddress(int id, String editorEmail, String infoEmail, String adminEmail, String phone, String phone2,
			String address) {
		super();
		this.id = id;
		this.editorEmail = editorEmail;
		this.infoEmail = infoEmail;
		this.adminEmail = adminEmail;
		this.phone = phone;
		this.phone2 = phone2;
		Address = address;
	}

	@Override
	public String toString() {
		return "WebSiteAddress [id=" + id + ", editorEmail=" + editorEmail + ", infoEmail=" + infoEmail
				+ ", adminEmail=" + adminEmail + ", phone=" + phone + ", phone2=" + phone2 + ", Address=" + Address
				+ "]";
	}
	
	
	
}
