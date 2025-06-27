package com.olxseller.olx.serviceImp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.olxseller.olx.DTO.QuesAnsDTO;
import com.olxseller.olx.model.Product;
import com.olxseller.olx.model.QuestionAnswers;
import com.olxseller.olx.model.User;
import com.olxseller.olx.repository.ProductRepository;
import com.olxseller.olx.repository.QuestionRepository;
import com.olxseller.olx.repository.UserRepository;
import com.olxseller.olx.service.QuesAnsService;

public class QuesAnsServiceImp implements QuesAnsService {
  @Autowired
  private QuestionRepository quesRepo;
  @Autowired
  private UserRepository userRepo;

  @Autowired
  private ProductRepository productRepo;

  @Override
  public QuesAnsDTO createQuesAns(QuesAnsDTO quesAnsDTO) {
    Optional<QuestionAnswers> existing = quesRepo.findById(quesAnsDTO.getId());
    if (existing.isPresent()) {
      QuestionAnswers quest = existing.get();
      BeanUtils.copyProperties(quesAnsDTO, quest, "id", "productId", "createdAt", "updatedAt", "userId");
      return toDTO(quesRepo.save(quest));
    } else {
      return toDTO(quesRepo.save(toEntity(quesAnsDTO)));
    }

  }

  @Override
  public void deleteQuesAns(int id) {
    if (quesRepo.existsById(id)) {
      quesRepo.deleteById(id);
    }
  }

  @Override
  public List<QuesAnsDTO> getQuesAnssByProductID(int productId) {
    return quesRepo.allQuestionsByProductID(productId).stream().map(this::toDTO).collect(Collectors.toList());
  }

  private QuestionAnswers toEntity(QuesAnsDTO dto) {
    QuestionAnswers quest = new QuestionAnswers();
    BeanUtils.copyProperties(dto, quest);
    User user = userRepo.findById(dto.getUserId())
        .orElseThrow(() -> new RuntimeException("User not found with ID: " + dto.getUserId()));
    Product product = productRepo.findById(dto.getProductId())
        .orElseThrow(() -> new RuntimeException("Product not found with ID: " + dto.getProductId()));
    quest.setProduct(product);
    quest.setUser(user);
    return quest;
  }

  private QuesAnsDTO toDTO(QuestionAnswers quest) {
    QuesAnsDTO dto = new QuesAnsDTO();
    BeanUtils.copyProperties(quest, dto);
    dto.setUserId(quest.getUser().getId());
    dto.setProductId(quest.getProduct().getId());
    return dto;
  }
}
