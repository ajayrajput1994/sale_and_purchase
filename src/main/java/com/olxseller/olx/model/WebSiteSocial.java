package com.olxseller.olx.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class WebSiteSocial {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String facebook;
	
	private String instagram;
	
	private String twitter;
	
	private String linkedin;
	
	private String gethub;
	
	private String Youtube;
	
	private String gmail;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getInstagram() {
		return instagram;
	}

	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getLinkedin() {
		return linkedin;
	}

	public void setLinkedin(String linkedin) {
		this.linkedin = linkedin;
	}

	public String getGethub() {
		return gethub;
	}

	public void setGethub(String gethub) {
		this.gethub = gethub;
	}

	public String getYoutube() {
		return Youtube;
	}

	public void setYoutube(String youtube) {
		Youtube = youtube;
	}

	public String getGmail() {
		return gmail;
	}

	public void setGmail(String gmail) {
		this.gmail = gmail;
	}

	public WebSiteSocial() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WebSiteSocial(int id, String facebook, String instagram, String twitter, String linkedin, String gethub,
			String youtube, String gmail) {
		super();
		this.id = id;
		this.facebook = facebook;
		this.instagram = instagram;
		this.twitter = twitter;
		this.linkedin = linkedin;
		this.gethub = gethub;
		Youtube = youtube;
		this.gmail = gmail;
	}

	@Override
	public String toString() {
		return "WebSiteSocial [id=" + id + ", facebook=" + facebook + ", instagram=" + instagram + ", twitter="
				+ twitter + ", linkedin=" + linkedin + ", gethub=" + gethub + ", Youtube=" + Youtube + ", gmail="
				+ gmail + "]";
	}
	
	
}
