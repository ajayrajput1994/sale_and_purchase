package com.olxseller.olx.serviceImp;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olxseller.olx.DTO.PaymentDTO;
import com.olxseller.olx.model.CustomerOrder;
import com.olxseller.olx.model.Payment;
import com.olxseller.olx.model.User;
import com.olxseller.olx.repository.OrderRepository;
import com.olxseller.olx.repository.PaymentRepository;
import com.olxseller.olx.repository.UserRepository;
import com.olxseller.olx.service.PaymentService;

@Service
public class PaymentServiceImp implements PaymentService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private PaymentRepository paymentRepository;

  @Override
  public PaymentDTO createPayment(PaymentDTO paymentDTO) {
    Payment existing = paymentRepository.paymentByOrderID(paymentDTO.getOrderId());
    System.out.println("payment existing:" + existing.getId());
    if (paymentRepository.paymentByOrderID(paymentDTO.getOrderId()) == null) {
      return toDTO(paymentRepository.save(toEntity(paymentDTO)));
    } else {
      return paymentDTO;
    }
  }

  @Override
  public PaymentDTO updatePayment(PaymentDTO paymentDTO) {
    Optional<Payment> existPayment = paymentRepository.findById(paymentDTO.getId());
    if (existPayment.isPresent()) {
      Payment payment = existPayment.get();
      BeanUtils.copyProperties(paymentDTO, payment, "id", "userId", "rzpOrderId", "orderId", "paymentDate",
          "updatedAt");
      return toDTO(paymentRepository.save(payment));
    } else {
      return toDTO(paymentRepository.save(toEntity(paymentDTO)));
    }
  }

  @Override
  public PaymentDTO updatePaymentStatus(int id, String status, String rzOrdId) {
    if (paymentRepository.existsById(id)) {
      Payment payment = paymentRepository.findById(id).get();
      payment.setStatus(status);
      return toDTO(paymentRepository.save(payment));
    } else {
      throw new RuntimeException("Payment not found with id" + id);
    }
  }

  @Override
  public PaymentDTO updatePaymentStatusAndPaymentID(int id, String status, String rzOrdId, String rzPayId,
      String method) {
    if (paymentRepository.existsById(id)) {
      Payment payment = paymentRepository.findById(id).get();
      payment.setStatus(status);
      payment.setRzpPaymentId(rzPayId);
      if (!method.isEmpty()) {
        payment.setPaymentMethod(method);
      }
      return toDTO(paymentRepository.save(payment));
    } else {
      throw new RuntimeException("Payment not found with id" + id);
    }
  }

  private Payment toEntity(PaymentDTO paymentDTO) {
    Payment payment = new Payment();
    // payment.setId(paymentDTO.getId());
    // payment.setAmount(paymentDTO.getAmount());
    // payment.setPaymentMethod(paymentDTO.getPaymentMethod());
    // payment.setStatus(paymentDTO.getStatus());
    BeanUtils.copyProperties(paymentDTO, payment);
    payment.setPaymentDate(LocalDateTime.now());
    payment.setUpdatedAt(LocalDateTime.now());
    CustomerOrder order = orderRepository.findById(paymentDTO.getOrderId())
        .orElseThrow(() -> new RuntimeException("Order Not Found with id" + paymentDTO.getOrderId()));
    User user = userRepository.findById(paymentDTO.getUserId())
        .orElseThrow(() -> new RuntimeException("User Not Found with id" + paymentDTO.getUserId()));
    payment.setOrder(order);
    payment.setUser(user);
    System.out.println("toEntity: " + payment.toString());
    return payment;
  }

  private PaymentDTO toDTO(Payment payment) {
    PaymentDTO paymentDTO = new PaymentDTO();
    // paymentDTO.setId(payment.getId());
    // paymentDTO.setAmount(payment.getAmount());
    // paymentDTO.setPaymentMethod(payment.getPaymentMethod());
    // paymentDTO.setStatus(payment.getStatus());
    BeanUtils.copyProperties(payment, paymentDTO);
    paymentDTO.setOrderId(payment.getOrder().getId());
    paymentDTO.setUserId(payment.getUser().getId());
    return paymentDTO;
  }

}
