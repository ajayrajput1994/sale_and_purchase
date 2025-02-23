package com.olxseller.olx.serviceImp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olxseller.olx.DTO.OrderDTO;
import com.olxseller.olx.helper.UniqueIdGenerator;
import com.olxseller.olx.model.Order;
import com.olxseller.olx.model.User;
import com.olxseller.olx.repository.OrderRepository;
import com.olxseller.olx.repository.UserRepository;
import com.olxseller.olx.service.OrderService;
@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UniqueIdGenerator idGenerator;

    @Override
    public OrderDTO saveOrder(OrderDTO OrderDTO) {
        // Order order=toEntity(OrderDTO);
        return toDTO(orderRepository.save(toEntity(OrderDTO)));
    }

    @Override
    public OrderDTO updateOrder(OrderDTO OrderDTO) {
        if(orderRepository.existsById(OrderDTO.getId())) {
            return toDTO(orderRepository.save(toEntity(OrderDTO)));
        }else{
            throw new RuntimeException("Order not found with id: " + OrderDTO.getId());
  
        }
    }


    @Override
    public void deleteOrder(int id) {
        if(orderRepository.existsById(id)){
            orderRepository.deleteById(id);
        }else{ 
            throw new RuntimeException("Order not found with id: "+id);
        }
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList()) ;  
    }

    @Override
    public Optional<OrderDTO> getOrderById(int id) {
        return orderRepository.findById(id).stream().map(this::toDTO).findFirst();
    }
    
    @Override
    public OrderDTO updateStatus(int id,String status) {
        if(orderRepository.existsById(id)){
           Order order=orderRepository.findById(id).get();
           order.setStatus(status);
           return toDTO(orderRepository.save(order));
        }else{ 
            throw new RuntimeException("Order not found with id: "+id);
        }
       
    }

   private Order toEntity(OrderDTO orderDTO){
    Order order=new Order();
    if(orderDTO.getId()==0){
        orderDTO.setOrderId(idGenerator.generateOrderId());
        orderDTO.setStatus("PENDING");
    }
    order.setId(orderDTO.getId());
    order.setOrderId(orderDTO.getOrderId());
    order.setCustomerName(orderDTO.getCustomerName());
    order.setItemDta(orderDTO.getItemDta());
    order.setBilling(orderDTO.getBilling());
    order.setShipping(orderDTO.getShipping());
    order.setVouchers(orderDTO.getVouchers());
    order.setGst(orderDTO.getGst());
    order.setVoucherDiscount(orderDTO.getVoucherDiscount());
    order.setHandlingFee(orderDTO.getHandlingFee());
    order.setProcessingFee(orderDTO.getProcessingFee());
    order.setSurgeFee(orderDTO.getSurgeFee());
    order.setDeliveryFee(orderDTO.getDeliveryFee());
    order.setTotalPrice(orderDTO.getTotalPrice());
    order.setGrandTotal(orderDTO.getGrandTotal());
    order.setStatus(orderDTO.getStatus());
    User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + orderDTO.getUserId()));
        order.setUser(user); 
    return order;
    }

    private OrderDTO toDTO(Order order){
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setCustomerName(order.getCustomerName());
        orderDTO.setItemDta(order.getItemDta());
        orderDTO.setBilling(order.getBilling());
        orderDTO.setShipping(order.getShipping());
        orderDTO.setVouchers(order.getVouchers());
        orderDTO.setGst(order.getGst());
        orderDTO.setVoucherDiscount(order.getVoucherDiscount());
        orderDTO.setHandlingFee(order.getHandlingFee());
        orderDTO.setProcessingFee(order.getProcessingFee());
        orderDTO.setSurgeFee(order.getSurgeFee());
        orderDTO.setDeliveryFee(order.getDeliveryFee());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setGrandTotal(order.getGrandTotal());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setUserId(order.getUser().getId());
    return orderDTO;
    }

    @Override
    public List<OrderDTO> getAllOrdersByUserID(int userId) {
        if(userRepository.existsById(userId)){
            return orderRepository.AllOrdersByUserID(userId).stream().map(this::toDTO).collect(Collectors.toList());
        }else{ 
            throw new RuntimeException("Order not found with id: "+userId);
        }
    }

  
}
