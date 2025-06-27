package com.olxseller.olx.serviceImp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.olxseller.olx.DTO.OrderDTO;
import com.olxseller.olx.DTO.ProductDTO;
import com.olxseller.olx.helper.ResponseData;
import com.olxseller.olx.helper.UniqueIdGenerator;
import com.olxseller.olx.model.CustomerOrder;
import com.olxseller.olx.model.User;
import com.olxseller.olx.repository.OrderRepository;
import com.olxseller.olx.repository.UserRepository;
import com.olxseller.olx.service.OrderService;
import com.olxseller.olx.service.ProductService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class OrderServiceImpl implements OrderService {
    @Value("${razorpay.keyID}")
    private String razorpayKeyId;
    @Value("${razorpay.secreteKey}")
    private String razorpaySecredKey;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UniqueIdGenerator idGenerator;
    @Autowired
    private ProductService productService;
    @Autowired
    public ResponseData responseData;

    @Override
    public OrderDTO saveOrder(OrderDTO OrderDTO) {
        CustomerOrder ord = orderRepository.save(toEntity(OrderDTO));
        System.out.println("saveOrder" + ord.getId());
        return toDTO(ord);
    }

    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO) {
        Optional<CustomerOrder> existOrder = orderRepository.findById(orderDTO.getId());
        if (existOrder.isPresent()) {
            CustomerOrder order = existOrder.get();
            BeanUtils.copyProperties(orderDTO, order, "id", "orderDate", "deliveredAt", "updatedAt", "userId",
                    "orderId");
            return toDTO(orderRepository.save(order));
        } else {
            throw new RuntimeException("Order not found with id: " + orderDTO.getId());

        }
    }

    @Override
    public void deleteOrder(int id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<OrderDTO> getOrderById(int id) {
        return orderRepository.findById(id).stream().map(this::toDTO).findFirst();
    }

    @Override
    public OrderDTO updateStatus(int id, String status) {
        if (orderRepository.existsById(id)) {
            CustomerOrder order = orderRepository.findById(id).get();
            order.setStatus(status);
            return toDTO(orderRepository.save(order));
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }

    }

    private CustomerOrder toEntity(OrderDTO orderDTO) {
        CustomerOrder order = new CustomerOrder();
        if (orderDTO.getId() == 0) {
            orderDTO.setOrderId(idGenerator.generateOrderId());
            orderDTO.setStatus("PENDING");
        }
        // order.setId(orderDTO.getId());
        // order.setOrderId(orderDTO.getOrderId());
        // order.setCustomerName(orderDTO.getCustomerName());
        // order.setItemDta(orderDTO.getItemDta());
        // order.setBilling(orderDTO.getBilling());
        // order.setShipping(orderDTO.getShipping());
        // order.setVouchers(orderDTO.getVouchers());
        // order.setGst(orderDTO.getGst());
        // order.setVoucherDiscount(orderDTO.getVoucherDiscount());
        // order.setHandlingFee(orderDTO.getHandlingFee());
        // order.setProcessingFee(orderDTO.getProcessingFee());
        // order.setSurgeFee(orderDTO.getSurgeFee());
        // order.setDeliveryFee(orderDTO.getDeliveryFee());
        // order.setTotalPrice(orderDTO.getTotalPrice());
        // order.setGrandTotal(orderDTO.getGrandTotal());
        // order.setStatus(orderDTO.getStatus());
        BeanUtils.copyProperties(orderDTO, order);
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + orderDTO.getUserId()));
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setDeliveredAt(LocalDateTime.now());
        System.out.println("toEntity:" + order.toString());
        return order;
    }

    private OrderDTO toDTO(CustomerOrder order) {
        OrderDTO orderDTO = new OrderDTO();
        // orderDTO.setId(order.getId());
        // orderDTO.setOrderId(order.getOrderId());
        // orderDTO.setCustomerName(order.getCustomerName());
        // orderDTO.setItemDta(order.getItemDta());
        // orderDTO.setBilling(order.getBilling());
        // orderDTO.setShipping(order.getShipping());
        // orderDTO.setVouchers(order.getVouchers());
        // orderDTO.setGst(order.getGst());
        // orderDTO.setVoucherDiscount(order.getVoucherDiscount());
        // orderDTO.setHandlingFee(order.getHandlingFee());
        // orderDTO.setProcessingFee(order.getProcessingFee());
        // orderDTO.setSurgeFee(order.getSurgeFee());
        // orderDTO.setDeliveryFee(order.getDeliveryFee());
        // orderDTO.setTotalPrice(order.getTotalPrice());
        // orderDTO.setGrandTotal(order.getGrandTotal());
        // orderDTO.setStatus(order.getStatus());
        BeanUtils.copyProperties(order, orderDTO);
        orderDTO.setUserId(order.getUser().getId());
        orderDTO.setOrderDate(order.getOrderDate());
        return orderDTO;
    }

    @Override
    public List<OrderDTO> getAllOrdersByUserID(int userId) {
        if (userRepository.existsById(userId)) {
            return orderRepository.AllOrdersByUserID(userId).stream().map(this::toDTO).collect(Collectors.toList());
        } else {
            throw new RuntimeException("Order not found with id: " + userId);
        }
    }

    private OrderDTO preOrder(Map<String, Object> requestDta) throws RazorpayException {
        OrderDTO orderDTO = new OrderDTO();
        System.out.println("preOrder:" + requestDta);
        int userId = Integer.parseInt(requestDta.get("userId").toString());
        String items = (String) requestDta.get("items");
        String billing = (String) requestDta.get("billing");
        double total = Double.parseDouble(requestDta.get("total").toString());
        User user = userRepository.findById(userId).get();
        Map<String, Integer> map = (Map<String, Integer>) responseData.getObjectFromJson(items);
        System.out.println("map:" + map);
        List<ProductDTO> productList = productService.getAllProductsByIds(responseData.getIntKeysFromMap(items));
        JSONObject notes = new JSONObject();
        List<Map<String, Object>> itemDta = new ArrayList<>();
        double gtotal = 0.0;
        for (Map.Entry<String, Integer> entery : map.entrySet()) {
            int id = Integer.parseInt(entery.getKey());
            int qty = entery.getValue();
            for (ProductDTO e : productList) {
                if (id == e.getId()) {
                    gtotal += qty * e.getPrice();
                    notes.put(e.getCode(), e.getName());
                    Map<String, Object> itm = new HashMap<>();
                    itm.put("id", e.getId());
                    // itm.put("q", e.getQuantity());
                    itm.put("q", qty);
                    itm.put("a", e.getPrice());
                    itemDta.add(itm);
                    break;
                }
            }
            ;
        }
        if (gtotal < 500) {
            gtotal += 40;
        }
        System.out.println("from ui grand total: " + total);
        System.out.println("Grand Total: " + gtotal);
        System.out.println("notes: " + notes);
        System.out.println("itemDta: " + itemDta);
        System.out.println("razorpayKeyId: " + razorpayKeyId);
        System.out.println("razorpaySecredKey: " + razorpaySecredKey);
        if (gtotal == total) {
            System.out.println("amt equal");
            orderDTO.setCustomerName(user.getName());
            orderDTO.setUserId(user.getId());
            orderDTO.setGst(18);
            orderDTO.setGrandTotal(gtotal);
            orderDTO.setTotalPrice(gtotal);
            orderDTO.setItemDta(responseData.getJsonStringFromObject(itemDta));
            orderDTO.setStatus("PENDING");
            orderDTO.setBilling(billing);
            orderDTO.setShipping(billing);
            orderDTO.setVouchers("pending");
            orderDTO.setVoucherDiscount(0);
            orderDTO.setHandlingFee(0);
            orderDTO.setProcessingFee(0);
            orderDTO.setSurgeFee(0);
            orderDTO.setDeliveryFee(0);
            orderDTO.setRzpOrderId("pending");
            orderDTO = saveOrder(orderDTO);
            System.out.println("create order ID" + orderDTO.getId());

            RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId, razorpaySecredKey);
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", gtotal * 100);
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", orderDTO.getOrderId());
            orderRequest.put("notes", notes);

            Order order = razorpayClient.orders.create(orderRequest);
            System.out.println("order: " + order);
            if (order.get("status").equals("created")) {
                orderDTO.setRzpOrderId(order.get("id"));
                orderDTO.setStatus("ON_HOLD");
                orderDTO = updateOrder(orderDTO);
            }

        } else {
            System.out.println("not equal");
        }
        return orderDTO;
    }

    @Override
    public OrderDTO createOrder(Map<String, Object> dta) {
        OrderDTO orderDTO = new OrderDTO();
        try {
            orderDTO = preOrder(dta);
        } catch (RazorpayException e) {
            System.out.println("createOrder Error:" + e);
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return orderDTO;
    }
}
