package com.olxseller.olx.service;

import java.util.List;
import org.springframework.stereotype.Component;
import com.olxseller.olx.model.Comments;

@Component
public interface CommentService {

  Comments createComment(Comments obj);
  
  List<Comments>  getCommentByBlogID(String blogId);

  Comments getCommentByID(int id);

  List<Comments> getAllComments();

  Comments updateComment(Comments obj,int id);

  void deleteCommentByID(int id);
}
