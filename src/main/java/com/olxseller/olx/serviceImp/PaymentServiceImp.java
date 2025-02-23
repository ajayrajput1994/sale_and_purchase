package com.olxseller.olx.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olxseller.olx.DTO.PaymentDTO;
import com.olxseller.olx.model.Order;
import com.olxseller.olx.model.Payment;
import com.olxseller.olx.model.User;
import com.olxseller.olx.repository.OrderRepository;
import com.olxseller.olx.repository.PaymentRepository;
import com.olxseller.olx.repository.UserRepository;
import com.olxseller.olx.service.PaymentService;
@Service
public class PaymentServiceImp implements PaymentService{
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private PaymentRepository paymentRepository;

  @Override
  public PaymentDTO createPayment(PaymentDTO paymentDTO) { 
    if(paymentRepository.paymentByOrderID(paymentDTO.getOrderId())==null){
      return  toDTO(paymentRepository.save(toEntity(paymentDTO)));
    }else{ 
      return paymentDTO;
    }
    }

  @Override
  public PaymentDTO updatePayment(PaymentDTO paymentDTO) {
    if(paymentRepository.existsById(paymentDTO.getId())){
      return toDTO(paymentRepository.save(toEntity(paymentDTO)));
    }else{
      throw new RuntimeException("Payment not found with id"+paymentDTO.getId());
    }
  }

  @Override
  public PaymentDTO updatePaymentStatus(int id, String status) {
    if(paymentRepository.existsById(id)){
      Payment payment=paymentRepository.findById(id).get();
      payment.setStatus(status);
      return toDTO(paymentRepository.save(payment));
    }else{
      throw new RuntimeException("Payment not found with id"+id);
    }
  }

  private Payment toEntity(PaymentDTO paymentDTO){
    Payment payment=new Payment();
    payment.setId(paymentDTO.getId());
    payment.setAmount(paymentDTO.getAmount());
    payment.setPaymentMethod(paymentDTO.getPaymentMethod());
    payment.setStatus(paymentDTO.getStatus());
    Order order=orderRepository.findById(paymentDTO.getOrderId())
    .orElseThrow(()-> new RuntimeException("Order Not Found with id"+paymentDTO.getOrderId()));
    User user=userRepository.findById(paymentDTO.getUserId())
    .orElseThrow(()-> new RuntimeException("User Not Found with id"+paymentDTO.getUserId()));
    payment.setOrder(order);
    payment.setUser(user);
    return payment;
  }

  private PaymentDTO toDTO(Payment payment){
    PaymentDTO paymentDTO=new PaymentDTO();
    paymentDTO.setId(payment.getId());
    paymentDTO.setAmount(payment.getAmount());
    paymentDTO.setPaymentMethod(payment.getPaymentMethod());
    paymentDTO.setStatus(payment.getStatus());
    paymentDTO.setOrderId(payment.getOrder().getId());
    paymentDTO.setUserId(payment.getUser().getId());
    return paymentDTO;
  }
  
}
