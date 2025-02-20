package com.olxseller.olx.serviceImp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olxseller.olx.DTO.OrderDTO;
import com.olxseller.olx.DTO.ProductDTO;
import com.olxseller.olx.model.Order;
import com.olxseller.olx.model.Product;
import com.olxseller.olx.model.User;
import com.olxseller.olx.repository.UserRepository;
import com.olxseller.olx.service.OrderService;
@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public OrderDTO saveOrder(OrderDTO OrderDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveOrder'");
    }

    @Override
    public OrderDTO updateOrder(OrderDTO OrderDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOrder'");
    }

    @Override
    public OrderDTO updatePriceAndQty(OrderDTO OrderDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePriceAndQty'");
    }

    @Override
    public void deleteOrder(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteOrder'");
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllOrders'");
    }

    @Override
    public Optional<OrderDTO> getOrderById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrderById'");
    }

   private Order toEntity(OrderDTO orderDTO){
    Order order=new Order();
    order.setId(orderDTO.getId());
    order.setUserName(orderDTO.getUserName());
    order.setTotalPrice(orderDTO.getTotalPrice());
    order.setStatus(orderDTO.getStatus());
    order.setProductDta(orderDTO.getProductDta());
    order.setQuantity(orderDTO.getQuantity());
    User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + orderDTO.getUserId()));
        order.setUser(user); 
    return order;
    }

    private OrderDTO toDTO(Order order){
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setUserName(order.getUserName());
        orderDTO.setProductDta(order.getProductDta());
        orderDTO.setQuantity(order.getQuantity());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setUserId(order.getUser().getId());

    return orderDTO;
    }
  
}
