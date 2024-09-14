package com.olxseller.olx.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olxseller.olx.model.Comments;
import com.olxseller.olx.repository.CommentsRepository;
import com.olxseller.olx.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
  @Autowired
  public CommentsRepository commRepo;

  @Override
  public Comments createComment(Comments obj) {
    return commRepo.save(obj);
  }

  @Override
  public List<Comments> getCommentByBlogID(String blogId) {
    return commRepo.getAllCommentsByBlogID(blogId);
  }

  @Override
  public Comments getCommentByID(int id) {
    return commRepo.findById(id).get();
  }

  @Override
  public Comments updateComment(Comments obj, int id) {
    Comments comm=commRepo.findById(id).get();
    comm.setName(obj.getName());
    comm.setEmail(obj.getEmail());
    comm.setDescription(obj.getDescription());
    comm.setSubject(obj.getSubject());
    comm.setUpdate_at(obj.getUpdate_at());
    return commRepo.save(comm);
  }

  @Override
  public void deleteCommentByID(int id) {
   commRepo.deleteById(id);
  }

  
}
