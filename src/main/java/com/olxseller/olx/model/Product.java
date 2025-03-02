package com.olxseller.olx.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.olxseller.olx.helper.AuditListener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@EntityListeners(AuditingEntityListener.class)
// @EntityListeners({AuditListener.class,AuditingEntityListener.class})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  
  @NotBlank
  private String code;
  
  @NotBlank
  private String name;
  
  @NotBlank
  @Column(length = 1000)
  private String description;
  
  @NotNull
  private double price;
  
  @NotNull
  private int quantity;
  
  @NotBlank
  private String image;

  @NotBlank
  private String category;

  @NotBlank
  private String subCategory;
  
  @ManyToOne
  @JsonBackReference
  private User user;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdAt;
  
  @LastModifiedDate
  private LocalDateTime updatedAt;

  
  
  
}

