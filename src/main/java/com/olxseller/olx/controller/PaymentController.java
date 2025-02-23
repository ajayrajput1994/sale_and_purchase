package com.olxseller.olx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olxseller.olx.DTO.PaymentDTO;
import com.olxseller.olx.service.PaymentService;

@RestController
@Validated
@RequestMapping("/payment")
public class PaymentController {

   @Autowired
   private PaymentService paymentService;

   @PostMapping
   public ResponseEntity<PaymentDTO> createPayment(@RequestBody @Validated PaymentDTO paymentDTO){
    return ResponseEntity.ok(paymentService.createPayment(paymentDTO));
   }
 

  @PutMapping("/{id}")
  public ResponseEntity<PaymentDTO> updatePayment(@PathVariable int id,@Validated @RequestBody PaymentDTO paymentDTO) {
    paymentDTO.setId(id);
    PaymentDTO payment = paymentService.updatePayment(paymentDTO);
    return ResponseEntity.ok(payment);
  }
  @PutMapping("/{id}/{status}")
  public ResponseEntity<PaymentDTO> updatePaymentStatus(@PathVariable int id,@PathVariable String status) {
    PaymentDTO payment = paymentService.updatePaymentStatus(id,status);
    return ResponseEntity.ok(payment);
  }
}
