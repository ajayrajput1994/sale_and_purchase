package com.olxseller.olx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.olxseller.olx.model.UserSettings;
import com.olxseller.olx.service.UserSettingsService;

public class UserSettingApiController {

  @Autowired
  private UserSettingsService userSettingsService;

  /**
   * Get seller settings
   */
  @GetMapping("/{userId}")
  public ResponseEntity<UserSettings> getUserSettings(@PathVariable int userId) {
    try {
      UserSettings settings = userSettingsService.getUserSettings(userId);
      return ResponseEntity.ok(settings);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  /**
   * Update seller settings
   */
  @PutMapping("/{userId}")
  public ResponseEntity<UserSettings> updateUserSettings(
      @PathVariable int userId,
      @RequestBody UserSettings settings) {
    try {
      userSettingsService.validateSettings(settings);
      UserSettings updatedSettings = userSettingsService.updateUserSettings(userId, settings);
      return ResponseEntity.ok(updatedSettings);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  /**
   * Reset settings to default
   */
  @PostMapping("/{userId}/reset")
  public ResponseEntity<UserSettings> resetSettings(@PathVariable int userId) {
    try {
      UserSettings resetSettings = userSettingsService.resetToDefault(userId);
      return ResponseEntity.ok(resetSettings);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  /**
   * Create default settings
   */
  @PostMapping("/{userId}/create-default")
  public ResponseEntity<UserSettings> createDefaultSettings(@PathVariable int userId) {
    try {
      UserSettings settings = userSettingsService.createDefaultSettings(userId);
      return ResponseEntity.ok(settings);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }
}