package com.olxseller.olx.model;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.olxseller.olx.helper.AuditListener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

	private static final long serialVersionUID=1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotNull 
	private String name="";
	@NotNull
	@Column(unique=true) 
	private String email="";
	@NotNull 
	private String phone="";
	@NotNull
	private String other_phone="";
	@NotNull 
	private String password="";
	@NotNull
	private String passwordStr="";
	@NotNull
	private String image="default.png";
	@NotNull
	private String role="ROLE_USER";
	@NotNull
	private Boolean enabled=true; 
	private Boolean agreed=true;
	
	@NotNull
	@CreatedDate
	private String create_at;
	
	@NotNull
	@LastModifiedDate
	private String update_at;
	@NotNull
	private String passcode="";
	@NotNull
	private String wishList="[default]";
	
	@NotNull
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="user")
	@BatchSize(size = 10)
	private Set<Blog> blog=new HashSet<>();
	@NotNull
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="user")
	@BatchSize(size = 10)
	private Set<UserAddress> addresses=new HashSet<>();
	// private List<UserAddress> addresses=new ArrayList<>();
	@NotNull
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="user")
	@BatchSize(size = 10)
	private Set<Order> order=new HashSet<>();
	@NotNull
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="user")
	@BatchSize(size = 10)
	private Set<Payment> payments=new HashSet<>();
	@NotNull
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="user")
	@BatchSize(size = 10)
	private Set<Review> reviews=new HashSet<>();
	@NotNull
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="user")
	@BatchSize(size = 10)
	@JsonManagedReference
	private Set<Product> product=new HashSet<>();

	public User(String email,String pass,String cdate,String udate) {
		super(); 
		this.email=email;
		this.password=pass;
		this.create_at=cdate;
		this.update_at=udate;
	}


	
}
