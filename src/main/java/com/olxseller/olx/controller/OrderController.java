package com.olxseller.olx.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.olxseller.olx.DTO.OrderDTO;
import com.olxseller.olx.DTO.PaymentDTO;
import com.olxseller.olx.service.OrderService;
import com.olxseller.olx.service.PaymentService;

@RestController
@Validated
@RequestMapping("/order")
public class OrderController {
        @Autowired
        private OrderService orderService;
        @Autowired
        private PaymentService paymentService;

        @PostMapping("/payment")
        public ResponseEntity<PaymentDTO> createPayment(@Valid @RequestBody PaymentDTO paymentDTO) {
            PaymentDTO payment = paymentService.createPayment(paymentDTO);
            return ResponseEntity.ok(payment);
        }
        @PostMapping
        public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
            OrderDTO newOrder = orderService.saveOrder(orderDTO);
            return ResponseEntity.ok(newOrder);
        }

        @PutMapping("/{id}")
        public ResponseEntity<OrderDTO> updateOrder(@PathVariable int id,@Valid @RequestBody OrderDTO orderDTO) {
            orderDTO.setId(id);
            OrderDTO newOrder = orderService.updateOrder(orderDTO);
            return ResponseEntity.ok(newOrder);
        }

        @PutMapping("/status")
        public ResponseEntity<OrderDTO> updateOrderStatus(@RequestParam("id") int id,@RequestParam("status") String status) {
            // orderDTO.setId(id);
            OrderDTO newOrder = orderService.updateStatus(id,status);
            return ResponseEntity.ok(newOrder);
        }

        @GetMapping("/{id}")
        public ResponseEntity<OrderDTO> getOrderById(@PathVariable int id) {
            return orderService.getOrderById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
           
        }
        @GetMapping
        public ResponseEntity<List<OrderDTO>> getAllOrder() {
            List<OrderDTO> orders=orderService.getAllOrders();
            return ResponseEntity.ok(orders);
           
        }
        @GetMapping("/user/{userId}")
        public ResponseEntity<List<OrderDTO>> getOrdersByUserID(@PathVariable int userId) {
            List<OrderDTO> orders=orderService.getAllOrdersByUserID(userId);
            return ResponseEntity.ok(orders);
           
        }
}
