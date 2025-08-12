package com.olxseller.olx.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.olxseller.olx.DTO.OrderDTO;
import com.olxseller.olx.DTO.UserDTO;
import com.olxseller.olx.DTO.WishlistDTO;
import com.olxseller.olx.helper.ResponseData;
import com.olxseller.olx.model.User;
import com.olxseller.olx.service.CartService;
import com.olxseller.olx.service.MainCategoryService;
import com.olxseller.olx.service.OrderService;
import com.olxseller.olx.service.ProductService;
import com.olxseller.olx.service.ReviewService;
import com.olxseller.olx.service.SubCategoryService;
import com.olxseller.olx.service.UserDtoService;
import com.olxseller.olx.service.UserService;
import com.olxseller.olx.service.WishlistService;

@Controller
@RequestMapping("/Customer")
public class CustomerController {

  final static private Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

  @Autowired
  public CartService cartService;
  @Autowired
  public WishlistService wishlistService;
  @Autowired
  public ProductService productService;
  @Autowired
  private UserDtoService uService;
  @Autowired
  public ResponseData responseData;
  @Autowired
  private UserService userservice;
  @Autowired
  private MainCategoryService catService;
  @Autowired
  private SubCategoryService subcatService;
  @Autowired
  private OrderService orderService;
  @Autowired
  private ReviewService reviewService;

  @GetMapping("/")
  public String customerHome(Model m, Principal principal, Authentication authentication) {
    System.out.println("=== CUSTOMER CONTROLLER ACCESSED ===");
    System.out.println("Principal: " + principal);
    System.out.println("Authentication: " + authentication);

    if (principal == null) {
      System.out.println("ERROR: Principal is null!");
      return "redirect:/signin?error=no-principal";
    }

    if (authentication == null) {
      System.out.println("ERROR: Authentication is null!");
      return "redirect:/signin?error=no-auth";
    }

    System.out.println("Principal name: " + principal.getName());
    System.out.println("Authentication authorities: " + authentication.getAuthorities());

    try {
      // Load common data
      loadCommonData(m, principal);

      System.out.println("Customer Home - Welcome: " + principal.getName());
      m.addAttribute("title", "Hi Dear Customer");
      m.addAttribute("disc", "Welcome to customer dashboard");

      return "customer/customer_account";

    } catch (Exception e) {
      System.err.println("Error in customer home: " + e.getMessage());
      e.printStackTrace();
      return "redirect:/signin?error=loading-failed";
    }
  }

  private void loadCommonData(Model m, Principal principal) {
    if (principal == null) {
      throw new RuntimeException("Principal cannot be null");
    }

    User user = userservice.findUserByEmail(principal.getName());
    if (user == null) {
      throw new RuntimeException("User not found: " + principal.getName());
    }

    Map<String, Object> map = new HashMap<>();

    // Load cart count
    List<Integer> cartCount = responseData.getIntKeysFromMap(cartService.getCartItems(user.getId()).getItems());
    WishlistDTO wish = wishlistService.getWishlist(user.getId());
    List<Integer> ids = responseData.getIntKeysFromMap(wish.getItems());

    m.addAttribute("cartCount", cartCount.isEmpty() ? 0 : cartCount.size());
    m.addAttribute("wishCount", ids.isEmpty() ? 0 : ids.size());

    // Populate map with data
    for (OrderDTO order : orderService.getAllOrdersByUserID(user.getId())) {
      responseData.getItemIdsFromOrdsetItemsString(order.getItemDta()).forEach((c) -> {
        if (!ids.contains(c)) {
          ids.add(c);
        }
      });
    }

    map.put("wishlistItems", wish.getItems());
    if (ids.isEmpty()) {
      map.put("productList", new ArrayList<>());
      map.put("reviews", new ArrayList<>());
    } else {
      map.put("productList", productService.getAllProductsByIds(ids));
      map.put("reviews", reviewService.getReviewsByProductIds(ids));
    }

    map.put("addressList", user.getAddresses());
    map.put("wishlist", user.getWishList());
    map.put("blogs", user.getBlog());
    map.put("cats", catService.getAllMainCategory());
    map.put("subcat", subcatService.getAllSubcat());
    map.put("orders", orderService.getAllOrdersByUserID(user.getId()));
    map.put("user", uService.findUserByEmail2(principal.getName()));

    var dta = responseData.jsonDataResponse("SUCCESS", "Home data loaded", map);
    m.addAttribute("data", dta);
    m.addAttribute("user", uService.findUserByEmail2(principal.getName()));
  }
}
