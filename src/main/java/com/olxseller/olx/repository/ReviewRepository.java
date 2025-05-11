package com.olxseller.olx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.olxseller.olx.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

	@Query("from Review as b where b.user.id=:userId")
	public List<Review> allReviewByUserID(@Param("userId") int userId);

	@Query("from Review as b where b.product.id=:productId")
	public List<Review> allReviewByProductID(@Param("productId") int productId);

	@Query("SELECT b FROM Review b JOIN FETCH b.product WHERE b.product.id IN :ids")
	public List<Review> allReviewByProductIds(@Param("ids") List<Integer> ids);
}
