package com.olxseller.olx.service;

import java.util.List;

import com.olxseller.olx.DTO.QuesAnsDTO;

public interface QuesAnsService {
  QuesAnsDTO createQuesAns(QuesAnsDTO quesAnsDTO);

  void deleteQuesAns(int id);

  // QuesAnsDTO getQuesAns(int id);

  List<QuesAnsDTO> getQuesAnssByProductID(int productId);

}
