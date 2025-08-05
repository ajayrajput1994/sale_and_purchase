package com.olxseller.olx.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olxseller.olx.model.UserSettings;
import com.olxseller.olx.repository.UserSettingRepository;

@Service
public class UserSettingsService {

  @Autowired
  private UserSettingRepository userSettingsRepository;

  /**
   * Get seller settings by seller ID, create default if not exists
   */
  public UserSettings getUserSettings(int userId) {
    Optional<UserSettings> settings = userSettingsRepository.findActiveSettingsByUserId(userId);

    if (settings.isPresent()) {
      return settings.get();
    } else {
      // Create default settings for new seller
      return createDefaultSettings(userId);
    }
  }

  /**
   * Create default settings for a seller
   */
  public UserSettings createDefaultSettings(int userId) {
    UserSettings settings = new UserSettings(userId);
    return userSettingsRepository.save(settings);
  }

  /**
   * Update seller settings
   */
  public UserSettings updateUserSettings(int userId, UserSettings updatedSettings) {
    UserSettings existingSettings = getUserSettings(userId);

    // Update only non-null values
    if (updatedSettings.getPlatformFeeRate() != null) {
      existingSettings.setPlatformFeeRate(updatedSettings.getPlatformFeeRate());
    }
    if (updatedSettings.getConvenienceFeeFlat() != null) {
      existingSettings.setConvenienceFeeFlat(updatedSettings.getConvenienceFeeFlat());
    }
    if (updatedSettings.getFreeDeliveryThreshold() != null) {
      existingSettings.setFreeDeliveryThreshold(updatedSettings.getFreeDeliveryThreshold());
    }
    if (updatedSettings.getStandardDeliveryFee() != null) {
      existingSettings.setStandardDeliveryFee(updatedSettings.getStandardDeliveryFee());
    }
    if (updatedSettings.getExpressDeliveryFee() != null) {
      existingSettings.setExpressDeliveryFee(updatedSettings.getExpressDeliveryFee());
    }
    if (updatedSettings.getMarkupRate() != null) {
      existingSettings.setMarkupRate(updatedSettings.getMarkupRate());
    }
    if (updatedSettings.getTaxRate() != null) {
      existingSettings.setTaxRate(updatedSettings.getTaxRate());
    }
    if (updatedSettings.getMinOrderValue() != null) {
      existingSettings.setMinOrderValue(updatedSettings.getMinOrderValue());
    }
    if (updatedSettings.getMaxOrderValue() != null) {
      existingSettings.setMaxOrderValue(updatedSettings.getMaxOrderValue());
    }
    if (updatedSettings.getIsExpressDeliveryEnabled() != null) {
      existingSettings.setIsExpressDeliveryEnabled(updatedSettings.getIsExpressDeliveryEnabled());
    }
    if (updatedSettings.getIsCodEnabled() != null) {
      existingSettings.setIsCodEnabled(updatedSettings.getIsCodEnabled());
    }
    if (updatedSettings.getCodCharges() != null) {
      existingSettings.setCodCharges(updatedSettings.getCodCharges());
    }

    return userSettingsRepository.save(existingSettings);
  }

  /**
   * Validate settings before saving
   */
  public boolean validateSettings(UserSettings settings) {
    if (settings.getPlatformFeeRate() < 0 || settings.getPlatformFeeRate() > 1) {
      throw new IllegalArgumentException("Platform fee rate must be between 0 and 1 (0-100%)");
    }
    if (settings.getConvenienceFeeFlat() < 0) {
      throw new IllegalArgumentException("Convenience fee cannot be negative");
    }
    if (settings.getFreeDeliveryThreshold() < 0) {
      throw new IllegalArgumentException("Free delivery threshold cannot be negative");
    }
    if (settings.getStandardDeliveryFee() < 0 || settings.getExpressDeliveryFee() < 0) {
      throw new IllegalArgumentException("Delivery fees cannot be negative");
    }
    if (settings.getMarkupRate() < 0) {
      throw new IllegalArgumentException("Markup rate cannot be negative");
    }
    if (settings.getTaxRate() < 0 || settings.getTaxRate() > 100) {
      throw new IllegalArgumentException("Tax rate must be between 0 and 100");
    }
    if (settings.getMinOrderValue() < 0) {
      throw new IllegalArgumentException("Minimum order value cannot be negative");
    }
    if (settings.getMaxOrderValue() < settings.getMinOrderValue()) {
      throw new IllegalArgumentException("Maximum order value must be greater than minimum order value");
    }

    return true;
  }

  /**
   * Reset settings to default
   */
  public UserSettings resetToDefault(int userId) {
    UserSettings existingSettings = getUserSettings(userId);

    // Reset to default values
    existingSettings.setPlatformFeeRate(0.05);
    existingSettings.setConvenienceFeeFlat(20.0);
    existingSettings.setFreeDeliveryThreshold(2000.0);
    existingSettings.setStandardDeliveryFee(40.0);
    existingSettings.setExpressDeliveryFee(50.0);
    existingSettings.setMarkupRate(0.30);
    existingSettings.setTaxRate(18.0);
    existingSettings.setMinOrderValue(0.0);
    existingSettings.setMaxOrderValue(50000.0);
    existingSettings.setIsExpressDeliveryEnabled(true);
    existingSettings.setIsCodEnabled(true);
    existingSettings.setCodCharges(0.0);

    return userSettingsRepository.save(existingSettings);
  }
}
