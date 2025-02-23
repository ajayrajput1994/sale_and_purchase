package com.olxseller.olx.helper;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.olxseller.olx.model.AuditTrail;
import com.olxseller.olx.repository.AuditTrailRepository;
@Component
public class AuditListener {
  

    @Autowired
    private AuditTrailRepository auditTrailRepository;

    @PrePersist
    @PreUpdate
    @PreRemove
    public void onEntityChange(Object entity) {
        AuditTrail auditTrail = new AuditTrail();
        auditTrail.setEntityName(entity.getClass().getSimpleName());
        // auditTrail.setEntityId(getEntityId(entity));
        auditTrail.setChangedBy(getCurrentUsername());
        auditTrail.setUsername(getCurrentUsername());
        // auditTrail.setUserId(getCurrentUserId());
        // auditTrail.setAction(getActionType());

        auditTrailRepository.save(auditTrail);
    }

    // private int getEntityId(Object entity) {
    //     // Implement logic to get the entity ID
    // }

    // private String getActionType() {
    //     // Implement logic to determine the action type (INSERT, UPDATE, DELETE)
    // }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    // private int getCurrentUserId() {
    //     // Implement logic to retrieve the user ID from the security context or user service
    // }
}
