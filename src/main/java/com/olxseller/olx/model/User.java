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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.olxseller.olx.helper.AuditListener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
// @EntityListeners({AuditListener.class,AuditingEntityListener.class})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {

	private static final long serialVersionUID=1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotNull
////@notblank(message="Name con't be empty !!")
	//@Size(min=3, max=12,message="Name must be between 3-12 characters !")
	private String name="";
	@NotNull
	@Column(unique=true)
//@Pattern(regexp="^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$",message="Invalid Email !!")
	private String email="";
	@NotNull
////@notblank(message="Phone con't be empty !!")
	//@Size(min=10, max=10,message="Phone must be between 10 digits !")
	private String phone="";
	@NotNull
	private String other_phone="";
	@NotNull
	////@notblank(message="Password can't be empty !")
	private String password="";
	@NotNull
	private String passwordStr="";
	@NotNull
	private String image="default.png";
	@NotNull
	private String role="ROLE_USER";
	@NotNull
	private Boolean enabled=true;
	//@AssertTrue(message="Must agree term and conditions !!")
	private Boolean agreed=true;
	
	@NotNull
	private String create_at;
	
	@NotNull
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

	public User(String email,String pass,String cdate,String udate) {
		super(); 
		this.email=email;
		this.password=pass;
		this.create_at=cdate;
		this.update_at=udate;
	}


	
}
