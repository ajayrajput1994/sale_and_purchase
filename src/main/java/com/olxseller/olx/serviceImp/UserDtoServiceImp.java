package com.olxseller.olx.serviceImp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.olxseller.olx.DTO.UserDTO;
import com.olxseller.olx.DTO.UserPasswordDTO;
import com.olxseller.olx.config.MyConfig;
import com.olxseller.olx.model.User;
import com.olxseller.olx.repository.UserRepository;
import com.olxseller.olx.service.UserDtoService;

@Service
public class UserDtoServiceImp implements UserDtoService {
  @Autowired
  private UserRepository userRepo;
  @Autowired
  private MyConfig  myConfig;
  
  @Transactional
  @Override
  public UserDTO newUser(UserDTO userDTO) {
    userDTO.setPassword(myConfig.passwordEncoder().encode(userDTO.getPassword()));
    userDTO.setRole("ROLE_USER");
    return toDTO(userRepo.save(toEntity(userDTO)));  
  }

  @Override
  public List<UserDTO> Users() {
    return userRepo.findAll().stream().map(this::toDTO).toList();  
  }

  @Override
  public UserDTO getUserByID2(int userId) {
  return userRepo.findById(userId).map(this::toDTO).orElseThrow(()->new RuntimeException("user not found with ID:"+userId));
  }

  @Override
  public UserDTO findUserByEmail2(String email) {
  return toDTO( userRepo.getUserByUserName(email));  
  }

  @Override
  public UserDTO updateUser2(UserDTO userDTO) {
    Optional<User> existUser=userRepo.findById(userDTO.getId());
    if(existUser.isPresent()){
      User user=existUser.get(); 
      BeanUtils.copyProperties(userDTO, user, "id","email","phone","role","create_at","update_at","password","passwordStr","image");
      return toDTO(userRepo.save(user));
    }else{
      throw new RuntimeException("User not found with ID: "+userDTO.getId());
    }
  }

  @Override
  public UserDTO updatePassword2(UserPasswordDTO userDTO) {
    Optional<User> existUser=userRepo.findById(userDTO.getId());
    if(existUser.isPresent()){
      User user=existUser.get(); 
      user.setPasswordStr(userDTO.getPassword());
      user.setPassword(myConfig.passwordEncoder().encode(userDTO.getPassword()));
      return toDTO(userRepo.save(user));
    }else{
      throw new RuntimeException("User not found with ID: "+userDTO.getId());
    }
  }

  @Override
  public void deleteUser2(int userId) {
   userRepo.deleteById(userId);  
  }

  //  private UserDTO toDTO(User user){
  //   System.out.println("user to dto: "+user.toString());
  //   UserDTO userDTO=new UserDTO();
  //   BeanUtils.copyProperties(user, userDTO);
  //   return userDTO;
  // }

  // private User toEntity(UserDTO userDTO){
  //   User user=new User();
  //   BeanUtils.copyProperties(userDTO, user);
  //   // if(userDTO.getOther_phone()==null){
  //   //   user.setOther_phone(userDTO.getPhone());
  //   // }
  //   System.out.println("user to toEntity: "+user.toString());
  //   return user;
  // }
  
  public  UserDTO toDTO(User user) {
    if (user == null) {
        return null;
    }
    return new UserDTO(
        user.getId(),
        user.getName(),
        user.getEmail(),
        user.getPhone(),
        user.getOther_phone(),
        user.getPassword(),
        user.getPasswordStr(),
        user.getImage(),
        user.getRole(),
        user.getEnabled(),
        user.getAgreed(),
        user.getCreate_at(),
        user.getUpdate_at(),
        user.getPasscode(),
        user.getWishList()
    );
}

public  User toEntity(UserDTO userDTO) {
  System.out.println("User toEntity: "+userDTO.toString());
    if (userDTO == null) {
        return null;
    }
    User user = new User();
    user.setId(userDTO.getId());
    user.setName(userDTO.getName());
    user.setEmail(userDTO.getEmail());
    user.setPhone(userDTO.getPhone());
    user.setOther_phone(userDTO.getOther_phone());
    user.setPassword(userDTO.getPassword());
    user.setPasswordStr(userDTO.getPasswordStr());
    user.setImage(userDTO.getImage());
    user.setRole(userDTO.getRole());
    user.setEnabled(userDTO.getEnabled());
    user.setAgreed(userDTO.getAgreed());
    user.setCreate_at(userDTO.getCreate_at());
    user.setUpdate_at(userDTO.getUpdate_at());
    user.setPasscode(userDTO.getPasscode());
    user.setWishList(userDTO.getWishList());
    return user;
}
}
