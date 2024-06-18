package com.olxseller.olx.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olxseller.olx.model.RegionState;
import com.olxseller.olx.repository.RegionStateRepository;
import com.olxseller.olx.service.StateService;
@Service
public class StateServiceImpl implements StateService {
  @Autowired
  private RegionStateRepository stateRepo;
  @Override
  public RegionState createState(RegionState st) {
    st.setImage("default.png");
    st.setPath(st.getStateName());
   return stateRepo.save(st);
  }

  @Override
  public RegionState updaateState(RegionState st, int id) {
    st.setStateId(id);
    return stateRepo.save(st);
  }

  @Override
  public void deleteState(int id) {
    stateRepo.deleteById(id);
  }
  
}
