package com.olxseller.olx.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.olxseller.olx.DTO.ProductDTO;

public interface ProductService {
    ProductDTO saveProduct(ProductDTO productDTO);
    ProductDTO updateProduct(ProductDTO productDTO);
    ProductDTO updatePriceAndQty(ProductDTO productDTO);
    void deleteProduct(int id);
    List<ProductDTO> getAllProducts();
    List<ProductDTO> getAllProductsByIds(List<Integer> ids);
    Page<ProductDTO> productScrolling(int index,int count,int userId);
    Optional<ProductDTO> getProductById(int id);
    List<ProductDTO> searchProductsByTxt(String txt);
  
}
