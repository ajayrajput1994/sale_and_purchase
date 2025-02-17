package com.olxseller.olx.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.olxseller.olx.config.MyConfig;
import com.olxseller.olx.model.User;
import com.olxseller.olx.repository.UserRepository;
import com.olxseller.olx.service.UserService;

@Component
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepository userRepo;
  @Autowired
  private MyConfig myConfig;
  @Override
  public User createUser(User user) {
    user.setImage("default.png");
    user.setPasswordStr(user.getPassword());
    user.setPassword(myConfig.passwordEncoder().encode(user.getPassword()));
    return userRepo.save(user);
  }

  @Override
  public User updateUser(User user, int id) {
    User u=userRepo.findById(id).get();
    // u.setEmail(user.getEmail());
    u.setName(user.getName());
    u.setPhone(user.getPhone());
    u.setOther_phone(user.getOther_phone());
    // u.setPasswordStr(user.getPassword());
    // u.setPassword(myConfig.passwordEncoder().encode(user.getPassword()));
    return userRepo.save(u);
  }

  @Override
  public void deleteUser(int id) {
    userRepo.deleteById(id);
  }

  @Override
  public User getUserByID(int id) {  
    return userRepo.findById(id).get();
  }

  @Override
  public List<User> AllUsers() {
    return userRepo.findAll();
  }

  @Override
  public User findUserByEmail(String email) {
    return userRepo.getUserByUserName(email);
  }

  @Override
  public User updatePassword(User user, int id) {
    User u=userRepo.findById(id).get();
    u.setPasswordStr(user.getPassword());
    u.setPassword(myConfig.passwordEncoder().encode(user.getPassword()));
    return userRepo.save(u);
  }
}
