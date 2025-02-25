package com.olxseller.olx.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.olxseller.olx.helper.AuditListener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "customer_order") 
@EntityListeners(AuditingEntityListener.class)
// @EntityListeners({AuditListener.class,AuditingEntityListener.class})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id; 
    @NotBlank
    private String orderId; 
    @ManyToOne
    private User user; 
    @NotBlank
    private String customerName; 
    @NotBlank
    private String itemDta;//"[]"
    @NotBlank
    private String billing;//"{}"
    @NotBlank
    private String shipping;//"{}" 
    @NotBlank
    private String vouchers;//"[]"
    @NotNull
    private int gst;
    @NotNull
    private double voucherDiscount;
    @NotNull
    private double handlingFee;
    @NotNull
    private double processingFee;
    @NotNull
    private double surgeFee;
    @NotNull
    private double deliveryFee;
    @NotNull
    private double totalPrice;
    @NotNull
    private double grandTotal;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime orderDate;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime deliveredAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @NotBlank
    private String status;

}
