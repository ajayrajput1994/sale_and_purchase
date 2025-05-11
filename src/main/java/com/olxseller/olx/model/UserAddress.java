package com.olxseller.olx.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @NotBlank
  private String name;
  @NotBlank
  private String city;
  @NotBlank
  private String state;
  @NotBlank
  private String region;
  @NotBlank
  private String phone;
  private String other_phone;
  @NotBlank
  private String pin_code;
  @NotBlank
  private String address;
  @NotBlank
  private String landmark;
  private String active;
  private String address_type;

  // @ManyToOne(cascade = CascadeType.ALL)
  @ManyToOne(cascade = CascadeType.MERGE)
  // @JsonIgnore
  @JsonBackReference
  private User user;

}
