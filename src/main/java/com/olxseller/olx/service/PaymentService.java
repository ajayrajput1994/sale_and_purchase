package com.olxseller.olx.service;

import com.olxseller.olx.DTO.PaymentDTO;

public interface PaymentService {
  PaymentDTO createPayment(PaymentDTO paymentDTO);

  PaymentDTO updatePayment(PaymentDTO paymentDTO);

  PaymentDTO updatePaymentStatus(int id, String status, String rzOrdId);

  PaymentDTO updatePaymentStatusAndPaymentID(int id, String status, String rzOrdId, String rzPayId, String method);
}
