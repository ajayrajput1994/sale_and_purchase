package com.olxseller.olx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olxseller.olx.DTO.ReviewDTO;
import com.olxseller.olx.service.ReviewService;

@RestController
@Validated
@RequestMapping("/review")
public class ReviewController {
  @Autowired
  private ReviewService reviewService;

  @PostMapping
  public ResponseEntity<ReviewDTO> createReview(@RequestBody @Validated ReviewDTO reviewDTO){
    return ResponseEntity.ok(reviewService.createReview(reviewDTO));
  }
  @PutMapping("/{id}")
  public ResponseEntity<ReviewDTO> updateReview(@PathVariable int id,@RequestBody @Validated ReviewDTO reviewDTO){
    reviewDTO.setId(id);
    return ResponseEntity.ok(reviewService.updateReview(reviewDTO));
  }
  @GetMapping("/{id}")
  public ResponseEntity<ReviewDTO> getReviewByID(@PathVariable int id){
    return ResponseEntity.ok(reviewService.getReview(id));
  }
  @GetMapping("/user/{id}")
  public ResponseEntity<List<ReviewDTO>> reviewsByUserID(@PathVariable int id){
    return ResponseEntity.ok(reviewService.getReviewsByUserID(id));
  }
  @GetMapping("/product/{id}")
  public ResponseEntity<List<ReviewDTO>> reviewsByProdctID(@PathVariable int id){
    return ResponseEntity.ok(reviewService.getReviewsByProductID(id));
  }


}
