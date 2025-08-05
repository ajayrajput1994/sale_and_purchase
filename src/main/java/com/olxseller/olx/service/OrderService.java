package com.olxseller.olx.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.olxseller.olx.DTO.OrderDTO;
import com.razorpay.RazorpayException;

public interface OrderService {
  OrderDTO createOrder(Map<String, Object> dta);

  OrderDTO saveOrder(OrderDTO OrderDTO);

  OrderDTO updateOrder(OrderDTO OrderDTO);

  OrderDTO updateStatus(int id, String status);

  void deleteOrder(int id);

  List<OrderDTO> getAllOrders();

  List<OrderDTO> getAllOrdersByUserID(int userId);

  Optional<OrderDTO> getOrderById(int id);

  String getPaymentMethod(String rzpayid) throws RazorpayException;
}
