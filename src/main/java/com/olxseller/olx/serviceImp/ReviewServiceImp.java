package com.olxseller.olx.serviceImp;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olxseller.olx.DTO.ReviewDTO;
import com.olxseller.olx.model.Product;
import com.olxseller.olx.model.Review;
import com.olxseller.olx.model.User;
import com.olxseller.olx.repository.ProductRepository;
import com.olxseller.olx.repository.ReviewRepository;
import com.olxseller.olx.repository.UserRepository;
import com.olxseller.olx.service.ReviewService;
@Service
public class ReviewServiceImp implements ReviewService{

  @Autowired
  private ReviewRepository reviewRepo;
  @Autowired
  private UserRepository userRepo;

  @Autowired
  private ProductRepository productRepo;
  @Override
  public ReviewDTO createReview(ReviewDTO reviewDTO) {
    return toDto(reviewRepo.save(toEntity(reviewDTO)));
    }

  @Override
  public ReviewDTO updateReview(ReviewDTO reviewDTO) {
    if(reviewRepo.existsById(reviewDTO.getId())){
      return toDto(reviewRepo.save(toEntity(reviewDTO)));
    }else{
      throw new RuntimeException("Review not found with ID: "+reviewDTO.getId());
    }
  }

  @Override
  public void deleteReview(int id, int userId, int productId) {
    reviewRepo.findById(id).ifPresentOrElse(review->reviewRepo.deleteById(id),
    ()->{throw new RuntimeException("Review not found with ID: "+id);}); 
  }

  @Override
  public ReviewDTO getReview(int id) {
    return reviewRepo.findById(id)
    .map(this::toDto)
    .orElseThrow(() -> new RuntimeException("Review not found"));
  }

  @Override
  public List<ReviewDTO> getReviewsByUserID(int userId) {
    return reviewRepo.allReviewByUserID(userId).stream().map(this::toDto).collect(Collectors.toList());
  }
  
  @Override
  public List<ReviewDTO> getReviewsByProductID(int productId) {
    return reviewRepo.allReviewByProductID(productId).stream().map(this::toDto).collect(Collectors.toList());
    }

  private ReviewDTO toDto(Review review){
    ReviewDTO reviewDTO=new ReviewDTO();
    reviewDTO.setId(review.getId());
    reviewDTO.setRating(review.getRating());
    reviewDTO.setReview(review.getReview());
    reviewDTO.setUserId(review.getUser().getId());
    reviewDTO.setProductId(review.getProduct().getId());
    return reviewDTO;
  }
  
  private Review toEntity(ReviewDTO reviewDTO){
    Review review=new Review();
    review.setId(reviewDTO.getId());
    review.setRating(reviewDTO.getRating());
    review.setReview(reviewDTO.getReview());
    User user=userRepo.findById(reviewDTO.getUserId()).orElseThrow(()->new RuntimeException("User not found with ID: "+reviewDTO.getUserId()));
    Product product=productRepo.findById(reviewDTO.getProductId()).orElseThrow(()->new RuntimeException("Product not found with ID: "+reviewDTO.getProductId()));
    review.setUser(user);
    review.setProduct(product);
    return review;
  }
  
}
