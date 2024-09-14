package com.olxseller.olx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.olxseller.olx.model.Comments;

@Repository
public interface CommentsRepository extends JpaRepository<Comments,Integer>{
  @Query("from Comments as c where c.postid=:blogId ")
  public List<Comments> getAllCommentsByBlogID(@Param("blogId") String blogId);
}
