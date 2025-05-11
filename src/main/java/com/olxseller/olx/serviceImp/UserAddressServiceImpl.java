package com.olxseller.olx.serviceImp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olxseller.olx.DTO.UserAddressDTO;
import com.olxseller.olx.controller.AdminController;
import com.olxseller.olx.model.User;
import com.olxseller.olx.model.UserAddress;
import com.olxseller.olx.repository.UserAddressRepo;
import com.olxseller.olx.repository.UserRepository;
import com.olxseller.olx.service.UserAddressService;

@Service
public class UserAddressServiceImpl implements UserAddressService {
  private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

  @Autowired
  private UserAddressRepo repo;
  @Autowired
  private UserRepository userRepository;

  @Override
  public UserAddress AddAddress(UserAddress obj) {
    return repo.save(obj);
  }

  @Override
  public UserAddress getAddress(int id) {
    return repo.findById(id).get();
  }

  @Override
  public UserAddress UpdateAddress(UserAddress obj, int id, int uid) {
    obj.setId(id);
    return repo.save(obj);
  }

  @Override
  public void deleteAddress(int id, int uid) {
    UserAddress address = repo.findById(id).get();
    address.setUser(null);
    repo.delete(address);
  }

  @Override
  public void setDefaultAddress(int id) {
    repo.findAll().forEach(e -> {
      e.setActive("");
      repo.save(e);
    });
    UserAddress address = repo.getReferenceById(id);
    address.setActive("yes");
    repo.save(address);
  }

  @Override
  public UserAddressDTO createAddress(UserAddressDTO obj) {
    Optional<UserAddress> exist = repo.findById(obj.getId());
    if (exist.isPresent()) {
      UserAddress address = exist.get();
      BeanUtils.copyProperties(obj, address, "id", "userId");
      LOGGER.info("Address update:{}", address.toString());
      return toDto(repo.save(address));
    } else {
      LOGGER.info("address Create:{}", obj.toString());
      return toDto(repo.save(toEntity(obj)));
    }
  }

  @Override
  public UserAddressDTO getAddressByid(int id) {
    Optional<UserAddress> product = repo.findById(id);
    LOGGER.info("Address get by id:{}", id);
    if (product.isPresent()) {
      return product.map(this::toDto).get();
    } else {

      return new UserAddressDTO();
    }
  }

  @Override
  public List<UserAddressDTO> getAllAddressByUserId(int uid) {
    return repo.getAddressesByUserId(uid).stream()
        .map(this::toDto)
        .collect(Collectors.toList());
  }

  private UserAddressDTO toDto(UserAddress address) {
    UserAddressDTO dto = new UserAddressDTO();
    BeanUtils.copyProperties(address, dto);
    dto.setUserId(address.getUser().getId());
    return dto;
  }

  private UserAddress toEntity(UserAddressDTO dto) {
    UserAddress address = new UserAddress();
    BeanUtils.copyProperties(dto, address);
    User user = userRepository.findById(dto.getUserId())
        .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));
    address.setUser(user);
    return address;
  }
}
