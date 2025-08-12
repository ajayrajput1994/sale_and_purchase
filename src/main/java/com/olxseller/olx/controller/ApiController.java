package com.olxseller.olx.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.olxseller.olx.DTO.CartDTO;
import com.olxseller.olx.DTO.OrderDTO;
import com.olxseller.olx.DTO.PaymentDTO;
import com.olxseller.olx.DTO.UserAddressDTO;
import com.olxseller.olx.DTO.UserDTO;
import com.olxseller.olx.config.MyConfig;
import com.olxseller.olx.helper.ResponseData;
import com.olxseller.olx.model.MainCategory;
import com.olxseller.olx.model.User;
import com.olxseller.olx.service.BlogService;
import com.olxseller.olx.service.CartService;
import com.olxseller.olx.service.CategoryService;
import com.olxseller.olx.service.MainCategoryService;
import com.olxseller.olx.service.OrderService;
import com.olxseller.olx.service.PaymentService;
import com.olxseller.olx.service.ProductService;
import com.olxseller.olx.service.SubCategoryService;
import com.olxseller.olx.service.UserAddressService;
import com.olxseller.olx.service.UserDtoService;
import com.olxseller.olx.service.UserService;
import com.olxseller.olx.service.WishlistService;
import com.razorpay.RazorpayException;

@RestController
@RequestMapping("/Api")
public class ApiController {
  private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);
  @Autowired
  public MyConfig myConfig;
  @Autowired
  public ResponseData responseData;
  @Autowired
  private UserService userService;
  @Autowired
  private UserDtoService userDtoService;
  @Autowired
  private MainCategoryService mainCatService;
  @Autowired
  private SubCategoryService subCatService;
  @Autowired
  private CategoryService catService;
  @Autowired
  private BlogService blogService;
  @Autowired
  private UserAddressService addressService;
  @Autowired
  private OrderService orderService;
  @Autowired
  private PaymentService paymentService;
  @Autowired
  private ProductService productService;
  @Autowired
  private CartService cartService;
  @Autowired
  private WishlistService wishlistService;

  @GetMapping("/home/{email}")
  public ResponseEntity<?> getHomeData(@PathVariable("email") String email) {

    System.out.println("Prepare home data....");
    try {
      UserDTO user = userDtoService.findUserByEmail2(email);
      Map<String, Object> map = new HashMap<>();
      map.put("user", user);
      map.put("product", productService.getAllProducts());
      map.put("cats", catService.getAllCategory());
      map.put("subcat", subCatService.allSubcats());
      map.put("maincat", mainCatService.AllCategories());
      map.put("address", addressService.getAllAddressByUserId(user.getId()));
      map.put("cart", cartService.getCartItems(user.getId()));
      map.put("wishlist", wishlistService.getWishlist(user.getId()));
      map.put("orders", orderService.getAllOrdersByUserID(user.getId()));
      return new ResponseEntity<>(
          responseData.jsonSimpleResponse("SUCCESS", "Successfuly Loading data", "LOADED",
              map),
          HttpStatus.OK);
    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    // return null;
  }

  @PostMapping("/MobLogin")
  public ResponseEntity<?> UserLogin(@RequestParam("email") String email, @RequestParam("password") String password) {
    LOGGER.info("Email: {}", email);
    LOGGER.info("Password: {}", password);
    try {
      UserDTO user = userDtoService.findUserByEmail2(email);
      Map<String, Object> map = new HashMap<>();
      map.put("user", user);
      map.put("product", productService.getAllProducts());
      map.put("cats", catService.getAllCategory());
      map.put("subcat", subCatService.allSubcats());
      map.put("maincat", mainCatService.AllCategories());
      map.put("address", addressService.getAllAddressByUserId(user.getId()));
      map.put("cart", cartService.getCartItems(user.getId()));
      map.put("wishlist", wishlistService.getWishlist(user.getId()));
      map.put("orders", orderService.getAllOrdersByUserID(user.getId()));
      return new ResponseEntity<>(
          responseData.jsonSimpleResponse("SUCCESS", "Successfuly Logged in", "LOADED",
              map),
          HttpStatus.OK);
    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    // return null;
  }

  @PostMapping("/MobSignup")
  public ResponseEntity<?> UserSignup(@RequestParam("email") String email, @RequestParam("password") String password,
      @RequestParam("name") String name, @RequestParam("phone") String phone,
      @RequestParam(required = false) Map<String, Object> dta) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
    String dat = sdf.format(new Date());
    System.out.println("name:" + name);
    System.out.println("email:" + email);
    System.out.println("phone:" + phone);
    System.out.println("password:" + password);
    try {
      UserDTO user = userDtoService.findUserByEmail2(email);
      if (user.getId() > 0) {

      } else {
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAgreed(true);
        user.setEnabled(true);
        user.setRole("ROLE_USER");
        user.setImage("default.png");
        user.setOther_phone("");
        user.setPasswordStr(user.getPassword());
        user.setPassword(myConfig.passwordEncoder().encode(user.getPassword()));
        user.setCreate_at(dat);
        user.setUpdate_at(dat);
      }
      // user = this.userService.createUser(user);
      System.out.println("user creating....." + user);
      Map<String, Object> map = new HashMap<>();
      map.put("user", user);
      map.put("cart", cartService.getCartItems(user.getId()));
      map.put("wishlist", wishlistService.getWishlist(user.getId()));
      return new ResponseEntity<>(
          responseData.jsonSimpleResponse("SUCCESS", "New Account Create Successfuly", "LOADED", map),
          HttpStatus.OK);
    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    // return null;
  }

  @PostMapping("/mob/Address/{uid}")
  public ResponseEntity<?> createUpdateAddressByMobile(@RequestParam Map<String, Object> dta,
      @PathVariable("uid") int uid) {
    System.out.println("requestData:" + dta);
    // SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
    // String dat = sdf.format(new Date());
    try {
      UserDTO user = userDtoService.getUserByID2(uid);
      UserAddressDTO address = new UserAddressDTO();
      address.setUserId(user.getId());
      address.setId(Integer.parseInt(dta.get("serverid").toString()));
      address.setName((String) dta.get("name"));
      address.setCity((String) dta.get("city"));
      address.setState((String) dta.get("state"));
      address.setRegion((String) dta.get("region"));
      address.setPhone((String) dta.get("phone"));
      address.setPin_code((String) dta.get("pin"));
      address.setAddress((String) dta.get("address"));
      address.setLandmark((String) dta.get("landmark"));
      address.setOther_phone((String) dta.get("otherPhone"));
      address.setAddress_type((String) dta.get("type"));
      address.setActive((String) dta.get("active"));
      System.out.println("address id :" + address.getId());
      String action = "UPDATE";
      if (address.getId() == 0) {
        action = "CREATE";
      }
      address = addressService.createAddress(address);
      System.out.println("address:" + address.toString());
      return new ResponseEntity<>(
          responseData.jsonSimpleResponse("SUCCESS", "Successfuly " + action, action, address),
          action == "CREATE" ? HttpStatus.CREATED : HttpStatus.OK);
    } catch (Exception ex) {
      System.out.println("Error:" + dta + "" + ex);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    // return null;
  }

  @PostMapping("/user/cart/{uid}")
  public ResponseEntity<?> updateCart(@RequestParam String items,
      @PathVariable("uid") int uid) {
    System.out.println("items:" + items);
    System.out.println("user id:" + uid);
    try {
      CartDTO cart = cartService.getCartItems(uid);
      cart.setItems(items);
      cart.setUserId(uid);
      String action = "UPDATE";
      if (cart.getId() == 0) {
        action = "CREATE";
      }
      return new ResponseEntity<>(
          responseData.jsonSimpleResponse("SUCCESS", "Successfuly " + action, action, cartService.addToCart(cart)),
          action == "CREATE" ? HttpStatus.CREATED : HttpStatus.OK);
    } catch (Exception ex) {
      System.out.println("Error: user id" + uid + "" + ex);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    // return null;
  }

  @PostMapping("/Address/create")
  public ResponseEntity<?> createUpdateUserAddress(@RequestBody UserAddressDTO address, Principal principal) {
    System.out.println("social:" + address);
    System.out.println("principal.getName():" + principal.getName());
    System.out.println(userService.findUserByEmail(principal.getName()));
    address.setUserId(userService.findUserByEmail(principal.getName()).getId());
    address.setActive("");
    // SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
    // String dat = sdf.format(new Date());
    try {
      System.out.println("address id :" + address.getId());
      String action = "UPDATE";
      if (address.getId() == 0) {
        action = "CREATE";
      }
      address = addressService.createAddress(address);
      System.out.println("address:" + address.toString());
      return new ResponseEntity<>(
          responseData.jsonSimpleResponse("SUCCESS", "Successfuly " + action, action, address),
          action == "CREATE" ? HttpStatus.CREATED : HttpStatus.OK);
    } catch (Exception ex) {
      System.out.println("Error:" + address.toString() + "" + ex);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    // return null;
  }

  @PostMapping("/info")
  public ResponseEntity<?> createUpdateUserInfo(@RequestBody User user, Principal principal) {
    System.out.println("user:" + user);
    // SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
    // String dat = sdf.format(new Date());
    try {
      // user.setId(userService.findUserByEmail(principal.getName()).getId());
      if (user.getId() > 0) {
        return new ResponseEntity<>(
            responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE",
                userService.updateUser(user, userService.findUserByEmail(principal.getName()).getId())),
            HttpStatus.OK);
      }
      return new ResponseEntity<>(
          responseData.jsonSimpleResponse("SUCCESS", "Successfuly Created", "CREATE", userService.createUser(user)),
          HttpStatus.CREATED);
    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    // return null;
  }

  @PostMapping("/password")
  public ResponseEntity<?> changePassword(@RequestBody User user, Principal principal) {
    System.out.println("user password:" + user);
    // SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
    // String dat = sdf.format(new Date());
    try {
      return new ResponseEntity<>(
          responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE",
              userService.updatePassword(user, userService.findUserByEmail(principal.getName()).getId())),
          HttpStatus.OK);

    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    // return null;
  }

  @GetMapping("/User/Address/{uid}")
  public ResponseEntity<?> getUserAddress(@PathVariable int uid) {
    System.out.println("getUserAddress:" + uid);
    try {
      return new ResponseEntity<>(
          responseData.jsonSimpleResponse("SUCCESS", "Successfuly LOADED", "LOADING",
              addressService.getAllAddressByUserId(uid)),
          HttpStatus.OK);

    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    // return null;
  }

  @GetMapping("/mob/Address/delete/{id}/{uid}")
  public ResponseEntity<?> deleteUserAddress(@PathVariable int id, @PathVariable int uid) {
    System.out.println("getUserAddress:" + uid);
    try {
      addressService.deleteAddress(id, uid);
      return new ResponseEntity<>(
          responseData.jsonSimpleResponse("SUCCESS", "Successfuly Delete", "DELETE",
              addressService.getAddressByid(id)),
          HttpStatus.OK);

    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    // return null;
  }

  @GetMapping("/Address/{id}")
  public ResponseEntity<?> setDefaultAddress(@PathVariable("id") int id) {
    System.out.println("setDefaultAddress:" + id);
    try {
      addressService.setDefaultAddress(id);
      return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE", id),
          HttpStatus.OK);

    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    // return null;
  }

  @PostMapping("/create-order")
  public ResponseEntity<?> OrderCreate(@RequestBody Map<String, Object> requestDta) throws RazorpayException {
    System.out.println("requestDta:" + requestDta);
    try {
      OrderDTO orderDTO = orderService.createOrder(requestDta);
      PaymentDTO paymentDTO = new PaymentDTO();
      paymentDTO.setUserId(orderDTO.getUserId());
      paymentDTO.setOrderId(orderDTO.getId());
      paymentDTO.setAmount(orderDTO.getGrandTotal());
      paymentDTO.setPaymentMethod("PENDING");
      paymentDTO.setRzpPaymentId("PENDING");
      paymentDTO.setRzpOrderId(orderDTO.getRzpOrderId());
      paymentDTO.setStatus("PENDING");
      paymentDTO = paymentService.updatePayment(paymentDTO);
      System.out.println("createPayment:" + paymentDTO.getId());
      return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS",
          "Successfuly Create Order", "CREATED", paymentDTO),
          HttpStatus.OK);

    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.OK);
    }
  }

  @PostMapping("/verify-payment")
  public ResponseEntity<?> VerifyPayment(@RequestBody Map<String, Object> requestDta) throws RazorpayException {
    System.out.println("requestDta:" + requestDta);
    String userId = (String) requestDta.get("userId");
    Integer payId = Integer.parseInt(requestDta.get("payId").toString());
    String rzyPaymentID = (String) requestDta.get("paymentId");
    String rzyOrderId = (String) requestDta.get("orderId");
    try {
      String method = orderService.getPaymentMethod(rzyPaymentID);
      PaymentDTO paymentDTO = paymentService.updatePaymentStatusAndPaymentID(payId, "SUCCESS", rzyOrderId,
          rzyPaymentID, method);
      OrderDTO orderDTO = orderService.updateStatus(paymentDTO.getOrderId(), "CREATED");
      System.out.println("update payment:" + paymentDTO.getId());
      System.out.println("update ORDER:" + orderDTO.getId());
      return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS",
          "Successfuly update Payment", "UPDATE", paymentDTO),
          HttpStatus.OK);

    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.OK);
    }
  }

  @PostMapping("/payment-failed")
  public ResponseEntity<?> PaymentFailed(@RequestBody Map<String, Object> requestDta) throws RazorpayException {
    System.out.println("requestDta:" + requestDta);
    Integer payId = Integer.parseInt(requestDta.get("payId").toString());
    String rzyPaymentID = (String) requestDta.get("paymentId");
    String rzyOrderId = (String) requestDta.get("orderId");
    try {
      PaymentDTO paymentDTO = paymentService.updatePaymentStatus(payId, "FAILED", rzyOrderId);
      OrderDTO orderDTO = orderService.updateStatus(paymentDTO.getOrderId(), "FAILED");
      System.out.println("update payment:" + paymentDTO.getId());
      System.out.println("update ORDER:" + orderDTO.getId());
      return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS",
          "Successfuly update Payment FAILER", "UPDATE", paymentDTO),
          HttpStatus.OK);

    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.OK);
    }
  }
}
