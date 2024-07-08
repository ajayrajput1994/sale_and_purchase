package com.olxseller.olx.serviceImp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.olxseller.olx.model.Banner;
import com.olxseller.olx.repository.BannerRepository;
import com.olxseller.olx.service.LogoService;
@Component
public class LogoServiceImpl implements LogoService{
  @Autowired
  private BannerRepository banRepo;

  @Override
  public Banner updateLogo(Banner ob, int id) {
    Optional<Banner> bn =banRepo.findById(id);
    if(bn.isPresent()){
      Banner ban=bn.get();
      ban.setLogo(ob.getLogo());
      return banRepo.save(ban);
    }
    return banRepo.save(ob);
  }

  @Override
  public Banner updateBanner(Banner ob, int id) {
    Optional<Banner> bn =banRepo.findById(id);
    if(bn.isPresent()){
      Banner ban=bn.get();
      ban.setTitle(ob.getTitle());
      ban.setBanner(ob.getBanner());
      return banRepo.save(ban);
    }
    return banRepo.save(ob);
  }

  
  
}
