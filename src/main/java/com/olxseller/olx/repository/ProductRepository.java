package com.olxseller.olx.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.olxseller.olx.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
  @Query("SELECT p FROM Product p WHERE " +
           "LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(p.price) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(p.category) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Product> searchProducts(@Param("searchTerm") String searchTerm);

    @Query("from Product as p where p.user.id=:userId")
    List<Product> getProductsByUserId(@Param("userId") String userId);

    @Query("from Product as p where p.id IN :ids")
    List<Product> getProductsByIds(@Param("ids") List<Integer> ids);

    @Query("from Product as p where p.user.id=:userId")
    Page<Product> ProductsByUserId(int userId,Pageable pageable);

    @Modifying
    @Transactional
    @Query("delete from Product as p where p.id=:id")
    public void deleteProduct(@Param("id") int id);
}
