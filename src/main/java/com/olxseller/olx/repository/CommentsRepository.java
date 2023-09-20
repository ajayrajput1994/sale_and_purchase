package com.olxseller.olx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.olxseller.olx.model.Comments;

@Repository
public interface CommentsRepository extends JpaRepository<Comments,Integer>{

}
