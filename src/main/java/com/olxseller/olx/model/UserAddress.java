package com.olxseller.olx.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class UserAddress  implements Serializable {

	private static final long serialVersionUID=1L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  private String name="";
  private String city="";
  private String state="";
  private String region="";
  private String phone="";
  private String other_phone="";
  private String pin_code="";
  private String address="";
  private String landmark="";
  private String active="";
  private String address_type="";
  @ManyToOne(cascade = CascadeType.ALL)
  @JsonIgnore
  private User user;
  
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
  public String getCity() {
    return city;
  }
  public void setCity(String city) {
    this.city = city;
  }
  public String getState() {
    return state;
  }
  public void setState(String state) {
    this.state = state;
  }
  public String getRegion() {
    return region;
  }
  public void setRegion(String region) {
    this.region = region;
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
  public String getPin_code() {
    return pin_code;
  }
  public void setPin_code(String pin_code) {
    this.pin_code = pin_code;
  }
  public String getAddress() {
    return address;
  }
  public void setAddress(String address) {
    this.address = address;
  }
  public String getLandmark() {
    return landmark;
  }
  public void setLandmark(String landmark) {
    this.landmark = landmark;
  }
  public String getAddress_type() {
    return address_type;
  }
  public void setAddress_type(String address_type) {
    this.address_type = address_type;
  }

  public User getUser() {
    return user;
  }
  public void setUser(User user) {
    this.user = user;
  }
  public String getActive() {
    return active;
  }
  public void setActive(String active) {
    this.active = active;
  }
  public UserAddress(){
    super();
  }

  public UserAddress(int id, String name, String city, String state, String region, String phone, String other_phone,
      String pin_code, String address, String landmark,String active, String address_type, User user) {
    this.id = id;
    this.name = name;
    this.city = city;
    this.state = state;
    this.region = region;
    this.phone = phone;
    this.other_phone = other_phone;
    this.pin_code = pin_code;
    this.address = address;
    this.landmark = landmark;
    this.address_type = address_type;
    this.active = active;
    this.user = user;
  }
  @Override
  public String toString() {
    return "UserAddress [id=" + id + ", name=" + name + ", city=" + city + ", state=" + state + ", region=" + region
        + ", phone=" + phone + ", other_phone=" + other_phone + ", pin_code=" + pin_code + ", address=" + address
        + ", landmark=" + landmark + ", active=" + active + ", address_type=" + address_type + ", user=" + user + "]";
  }
 
 

}
