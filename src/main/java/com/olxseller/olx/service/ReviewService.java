package com.olxseller.olx.service;

import java.util.List;

import com.olxseller.olx.DTO.ReviewDTO;

public interface ReviewService {
  ReviewDTO createReview(ReviewDTO ReviewDTO);

  ReviewDTO updateReview(ReviewDTO ReviewDTO);

  void deleteReview(int id, int userId, int productId);

  ReviewDTO getReview(int id);

  List<ReviewDTO> getReviewsByUserID(int userId);

  List<ReviewDTO> getReviewsByProductID(int productId);

  List<ReviewDTO> getReviewsByProductIds(List<Integer> ids);

}
