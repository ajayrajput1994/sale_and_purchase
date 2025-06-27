package com.olxseller.olx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.olxseller.olx.model.QuestionAnswers;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionAnswers, Integer> {

  @Query("from QuestionAnswers as b where b.product.id=:productId")
  public List<QuestionAnswers> allQuestionsByProductID(@Param("productId") int productId);
}
