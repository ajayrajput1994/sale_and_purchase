package com.olxseller.olx.service;

import java.util.List;
import java.util.Optional;
import com.olxseller.olx.DTO.OrderDTO;

public interface OrderService {
    OrderDTO saveOrder(OrderDTO OrderDTO);
    OrderDTO updateOrder(OrderDTO OrderDTO);
    OrderDTO updatePriceAndQty(OrderDTO OrderDTO);
    void deleteOrder(int id);
    List<OrderDTO> getAllOrders();
    Optional<OrderDTO> getOrderById(int id);
}
