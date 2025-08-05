package com.olxseller.olx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.olxseller.olx.model.UserSettings;

import java.util.Optional;

@Repository
public interface UserSettingRepository extends JpaRepository<UserSettings, Long> {

  Optional<UserSettings> findByUserIdAndIsActive(int userId, Boolean isActive);

  Optional<UserSettings> findByUserId(int userId);

  @Query("SELECT s FROM UserSettings s WHERE s.userId = :userId AND s.isActive = true")
  Optional<UserSettings> findActiveSettingsByUserId(@Param("userId") int userId);

  boolean existsByUserId(int userId);
}