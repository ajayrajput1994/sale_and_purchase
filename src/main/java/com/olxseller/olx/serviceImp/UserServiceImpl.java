package com.olxseller.olx.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.olxseller.olx.model.User;
import com.olxseller.olx.repository.UserRepository;
import com.olxseller.olx.service.UserService;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepository userRepo;
  @Autowired
  private BCryptPasswordEncoder passwordEncoder;
  @Override
  public User createUser(User user) {
    user.setImage("default.png");
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepo.save(user);
  }

  @Override
  public User updateUser(User user, int id) {
    User u=userRepo.findById(id).get();
    u.setEmail(user.getEmail());
    u.setName(user.getName());
    u.setPassword(user.getPassword());
    return userRepo.save(user);
  }

  @Override
  public void deleteUser(int id) {
    userRepo.deleteById(id);
  }
}
