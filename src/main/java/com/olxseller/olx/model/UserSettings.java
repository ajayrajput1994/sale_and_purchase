package com.olxseller.olx.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PreUpdate;

@Entity
public class UserSettings {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", nullable = false, unique = true)
  private int userId;

  // Fee Configuration
  @Column(name = "platform_fee_rate", nullable = false)
  private Double platformFeeRate = 0.05; // Default 5%

  @Column(name = "convenience_fee_flat", nullable = false)
  private Double convenienceFeeFlat = 20.0; // Default ₹20

  // Delivery Configuration
  @Column(name = "free_delivery_threshold", nullable = false)
  private Double freeDeliveryThreshold = 2000.0; // Default ₹2000

  @Column(name = "standard_delivery_fee", nullable = false)
  private Double standardDeliveryFee = 40.0; // Default ₹40

  @Column(name = "express_delivery_fee", nullable = false)
  private Double expressDeliveryFee = 50.0; // Default ₹50

  // Pricing Configuration
  @Column(name = "markup_rate", nullable = false)
  private Double markupRate = 0.30; // Default 30%

  @Column(name = "tax_rate", nullable = false)
  private Double taxRate = 18.0; // Default GST 18%

  // Additional Settings
  @Column(name = "min_order_value")
  private Double minOrderValue = 0.0; // Minimum order value

  @Column(name = "max_order_value")
  private Double maxOrderValue = 50000.0; // Maximum order value

  @Column(name = "is_express_delivery_enabled")
  private Boolean isExpressDeliveryEnabled = true;

  @Column(name = "is_cod_enabled")
  private Boolean isCodEnabled = true; // Cash on Delivery

  @Column(name = "cod_charges")
  private Double codCharges = 0.0; // COD charges

  // Audit Fields
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "is_active")
  private Boolean isActive = true;

  // Constructors
  public UserSettings() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  public UserSettings(int userId) {
    this();
    this.userId = userId;
  }

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public Double getPlatformFeeRate() {
    return platformFeeRate;
  }

  public void setPlatformFeeRate(Double platformFeeRate) {
    this.platformFeeRate = platformFeeRate;
    this.updatedAt = LocalDateTime.now();
  }

  public Double getConvenienceFeeFlat() {
    return convenienceFeeFlat;
  }

  public void setConvenienceFeeFlat(Double convenienceFeeFlat) {
    this.convenienceFeeFlat = convenienceFeeFlat;
    this.updatedAt = LocalDateTime.now();
  }

  public Double getFreeDeliveryThreshold() {
    return freeDeliveryThreshold;
  }

  public void setFreeDeliveryThreshold(Double freeDeliveryThreshold) {
    this.freeDeliveryThreshold = freeDeliveryThreshold;
    this.updatedAt = LocalDateTime.now();
  }

  public Double getStandardDeliveryFee() {
    return standardDeliveryFee;
  }

  public void setStandardDeliveryFee(Double standardDeliveryFee) {
    this.standardDeliveryFee = standardDeliveryFee;
    this.updatedAt = LocalDateTime.now();
  }

  public Double getExpressDeliveryFee() {
    return expressDeliveryFee;
  }

  public void setExpressDeliveryFee(Double expressDeliveryFee) {
    this.expressDeliveryFee = expressDeliveryFee;
    this.updatedAt = LocalDateTime.now();
  }

  public Double getMarkupRate() {
    return markupRate;
  }

  public void setMarkupRate(Double markupRate) {
    this.markupRate = markupRate;
    this.updatedAt = LocalDateTime.now();
  }

  public Double getTaxRate() {
    return taxRate;
  }

  public void setTaxRate(Double taxRate) {
    this.taxRate = taxRate;
    this.updatedAt = LocalDateTime.now();
  }

  public Double getMinOrderValue() {
    return minOrderValue;
  }

  public void setMinOrderValue(Double minOrderValue) {
    this.minOrderValue = minOrderValue;
    this.updatedAt = LocalDateTime.now();
  }

  public Double getMaxOrderValue() {
    return maxOrderValue;
  }

  public void setMaxOrderValue(Double maxOrderValue) {
    this.maxOrderValue = maxOrderValue;
    this.updatedAt = LocalDateTime.now();
  }

  public Boolean getIsExpressDeliveryEnabled() {
    return isExpressDeliveryEnabled;
  }

  public void setIsExpressDeliveryEnabled(Boolean isExpressDeliveryEnabled) {
    this.isExpressDeliveryEnabled = isExpressDeliveryEnabled;
    this.updatedAt = LocalDateTime.now();
  }

  public Boolean getIsCodEnabled() {
    return isCodEnabled;
  }

  public void setIsCodEnabled(Boolean isCodEnabled) {
    this.isCodEnabled = isCodEnabled;
    this.updatedAt = LocalDateTime.now();
  }

  public Double getCodCharges() {
    return codCharges;
  }

  public void setCodCharges(Double codCharges) {
    this.codCharges = codCharges;
    this.updatedAt = LocalDateTime.now();
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
    this.updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  public void preUpdate() {
    this.updatedAt = LocalDateTime.now();
  }

  @Override
  public String toString() {
    return "UserSettings{" +
        "id=" + id +
        ", userId=" + userId +
        ", platformFeeRate=" + platformFeeRate +
        ", convenienceFeeFlat=" + convenienceFeeFlat +
        ", freeDeliveryThreshold=" + freeDeliveryThreshold +
        ", standardDeliveryFee=" + standardDeliveryFee +
        ", expressDeliveryFee=" + expressDeliveryFee +
        ", markupRate=" + markupRate +
        ", taxRate=" + taxRate +
        ", isActive=" + isActive +
        '}';
  }
}
