package com.olxseller.olx.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olxseller.olx.DTO.ProductDTO;
import com.olxseller.olx.model.UserSettings;

@Service
public class OrderCalculationService {
  @Autowired
  private UserSettingsService userSettingsService;

  public static class ProductCalculation {
    private int id;
    private int qty;
    private double price;
    private double discount;
    private double taxRate;
    private double offerAmount;
    private UserSettings settings;

    // Constructor
    public ProductCalculation(int id, int qty, double price, double discount, double taxRate, double offerAmount,
        UserSettings settings) {
      this.id = id;
      this.qty = qty;
      this.price = price;
      this.discount = discount;
      this.taxRate = taxRate;
      this.offerAmount = offerAmount;
      this.settings = settings;
    }

    // Base subtotal
    public double getSubTotal() {
      return price * qty;
    }

    // Original total assuming markup from settings
    public double getOriginalTotal() {
      return (price * (1 + settings.getMarkupRate())) * qty;
    }

    // Total discount from field
    public double getTotalDiscount() {
      return discount * qty;
    }

    // Total offer from field
    public double getTotalOffer() {
      return offerAmount * qty;
    }

    // Tax amount (based on subtotal after discount & offer)
    public double getTaxAmount() {
      System.out.println("getTaxAmount:" + taxRate);
      return (getSubTotal() - getTotalDiscount() - getTotalOffer()) * (taxRate / 100);
    }

    // Platform fee using user settings
    public double getPlatformFee() {
      return getSubTotal() * settings.getPlatformFeeRate();
    }

    // Convenience fee using user settings
    public double getConvenienceFee() {
      return settings.getConvenienceFeeFlat() * qty;
    }

    // Delivery charges for this product using user settings
    public double getDeliveryCharges(boolean isExpressDelivery) {
      if (getSubTotal() >= settings.getFreeDeliveryThreshold()) {
        return 0.0;
      }
      return isExpressDelivery ? settings.getExpressDeliveryFee() : settings.getStandardDeliveryFee();
    }

    // Total amount for this product using user settings
    public double getTotalAmount(boolean isExpressDelivery) {
      double base = getSubTotal() - getTotalDiscount() - getTotalOffer();
      double tax = getTaxAmount();
      double platformFee = getPlatformFee();
      double deliveryFee = getDeliveryCharges(isExpressDelivery);

      return base + tax + platformFee + settings.getConvenienceFeeFlat() + deliveryFee;
    }

    // Total savings using user settings
    public double getSavings() {
      double mrSavings = getOriginalTotal() - getSubTotal();
      double deliverySaved = getSubTotal() >= settings.getFreeDeliveryThreshold() ? settings.getStandardDeliveryFee()
          : 0.0;
      return getTotalDiscount() + getTotalOffer() + deliverySaved + mrSavings;
    }

    // Getters
    public int getId() {
      return id;
    }

    public int getQty() {
      return qty;
    }

    public double getPrice() {
      return price;
    }
  }

  public static class OrderSummary {
    private List<ProductCalculation> items;
    private boolean isExpressDelivery;
    private UserSettings settings;

    public OrderSummary(List<ProductCalculation> items, boolean isExpressDelivery, UserSettings settings) {
      this.items = items;
      this.isExpressDelivery = isExpressDelivery;
      this.settings = settings;
    }

    // Total of all item subtotals
    public double getItemsSubtotal() {
      return items.stream().mapToDouble(ProductCalculation::getSubTotal).sum();
    }

    // Total original price
    public double getOriginalTotal() {
      return items.stream().mapToDouble(ProductCalculation::getOriginalTotal).sum();
    }

    // Total discount
    public double getTotalDiscount() {
      return items.stream().mapToDouble(ProductCalculation::getTotalDiscount).sum();
    }

    // Total offer amount
    public double getTotalOffer() {
      return items.stream().mapToDouble(ProductCalculation::getTotalOffer).sum();
    }

    // Total tax
    public double getTotalTax() {
      return items.stream().mapToDouble(ProductCalculation::getTaxAmount).sum();
    }

    // Total platform fee
    public double getTotalPlatformFee() {
      return items.stream().mapToDouble(ProductCalculation::getPlatformFee).sum();
    }

    // Total convenience fee
    public double getTotalConvenienceFee() {
      return items.stream().mapToDouble(ProductCalculation::getConvenienceFee).sum();
    }

    // Total delivery charges
    public double getTotalDeliveryCharges() {
      return items.stream().mapToDouble(item -> item.getDeliveryCharges(isExpressDelivery)).sum();
    }

    // Grand total payable
    public double getTotalAmount() {
      return items.stream().mapToDouble(item -> item.getTotalAmount(isExpressDelivery)).sum();
    }

    // Total savings
    public double getTotalSavings() {
      return items.stream().mapToDouble(ProductCalculation::getSavings).sum();
    }

    // Detailed breakdown for debugging
    public Map<String, Double> getBreakdown() {
      Map<String, Double> breakdown = new HashMap<>();
      breakdown.put("itemsSubtotal", getItemsSubtotal());
      breakdown.put("totalDiscount", getTotalDiscount());
      breakdown.put("totalOffer", getTotalOffer());
      breakdown.put("totalTax", getTotalTax());
      breakdown.put("totalPlatformFee", getTotalPlatformFee());
      breakdown.put("totalConvenienceFee", getTotalConvenienceFee());
      breakdown.put("totalDeliveryCharges", getTotalDeliveryCharges());
      breakdown.put("totalAmount", getTotalAmount());
      breakdown.put("totalSavings", getTotalSavings());
      return breakdown;
    }
  }

  /**
   * Calculate order total based on products and user-specific configuration
   * 
   * @param userId            User ID to get settings for
   * @param productList       List of products with their details
   * @param quantityMap       Map of product ID to quantity
   * @param isExpressDelivery Whether express delivery is selected
   * @return OrderSummary with all calculations
   */
  public OrderSummary calculateOrderTotal(int userId, List<ProductDTO> productList, Map<String, Integer> quantityMap,
      boolean isExpressDelivery) {
    // Get user-specific settings
    UserSettings settings = userSettingsService.getUserSettings(userId);

    List<ProductCalculation> calculations = new ArrayList<>();

    for (Map.Entry<String, Integer> entry : quantityMap.entrySet()) {
      int productId = Integer.parseInt(entry.getKey());
      int qty = entry.getValue();

      // Find matching product
      ProductDTO product = productList.stream()
          .filter(p -> p.getId() == productId)
          .findFirst()
          .orElse(null);

      if (product != null) {
        // Create calculation object with product details and user settings
        ProductCalculation calc = new ProductCalculation(
            product.getId(),
            qty,
            product.getPrice(),
            product.getDiscount(),
            settings.getTaxRate(), // Use tax rate from user settings
            product.getOfferAmount(),
            settings // Pass user settings to calculation
        );
        calculations.add(calc);
      }
    }

    return new OrderSummary(calculations, isExpressDelivery, settings);
  }

  /**
   * Validate if the frontend calculated total matches server calculation
   * 
   * @param frontendTotal         Total sent from frontend
   * @param serverCalculatedTotal Total calculated on server
   * @param tolerance             Acceptable difference (e.g., 0.01 for rounding
   *                              differences)
   * @return true if totals match within tolerance
   */
  public boolean validateTotal(double frontendTotal, double serverCalculatedTotal, double tolerance) {
    return Math.abs(frontendTotal - serverCalculatedTotal) <= tolerance;
  }
}