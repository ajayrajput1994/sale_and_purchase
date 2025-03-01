package com.olxseller.olx.helper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
  private static AuditTrailRepository auditTrailRepository;

//   @Autowired
//   public static void setAuditTrailRepository(AuditTrailRepository repository) {
//       auditTrailRepository = repository;
//   }
    @PrePersist
    public void onPrePersist(Object entity) {
        System.out.println("onPrePersist - auditTrailRepository: " + auditTrailRepository);
        createAuditTrail(entity, "INSERT");
    }

    @PreUpdate
    public void onPreUpdate(Object entity) {
        System.out.println("onPreUpdate - auditTrailRepository: " + auditTrailRepository);
        createAuditTrail(entity, "UPDATE");
    }

    @PreRemove
    public void onPreRemove(Object entity) {
        System.out.println("onPreRemove - auditTrailRepository: " + auditTrailRepository);
        createAuditTrail(entity, "DELETE");
    }

    private void createAuditTrail(Object entity, String actionType) {
        if (auditTrailRepository == null) {
            System.err.println("auditTrailRepository is null in createAuditTrail");
            return;
        }

        AuditTrail auditTrail = new AuditTrail();
        auditTrail.setEntityName(entity.getClass().getSimpleName());
        auditTrail.setEntityId(getEntityId(entity));
        auditTrail.setChangedBy(getCurrentUsername());
        auditTrail.setUsername(getCurrentUsername());
        auditTrail.setAction(actionType);

        auditTrailRepository.save(auditTrail);
    }
    private int getEntityId(Object entity) {
      try {
          Method getIdMethod = entity.getClass().getMethod("getId");
          int id = (int) getIdMethod.invoke(entity);
          System.out.println("Entity ID: " + id);
          return id;
      } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
          throw new RuntimeException("Failed to get entity ID", e);
      }
  }
  

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("principal AuditListener: "+principal);
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    // private int getCurrentUserId() {
    //     Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    //     if (principal instanceof UserDetails) {
    //         // Assuming your UserDetails implementation has a method getId()
    //         return ((CustomUserDetails) principal).getId();
    //     } else {
    //         // Handle cases where principal is not an instance of UserDetails
    //         throw new RuntimeException("User ID not found");
    //     }
    // }
}
