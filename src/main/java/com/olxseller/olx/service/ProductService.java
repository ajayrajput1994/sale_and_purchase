package com.olxseller.olx.service;

import java.util.List;
import java.util.Optional; 

import com.olxseller.olx.DTO.ProductDTO;

public interface ProductService {
    ProductDTO saveProduct(ProductDTO productDTO);
    ProductDTO updateProduct(ProductDTO productDTO);
    ProductDTO updatePriceAndQty(ProductDTO productDTO);
    void deleteProduct(int id);
    List<ProductDTO> getAllProducts();
    Optional<ProductDTO> getProductById(int id);
    List<ProductDTO> searchProductsByTxt(String txt);
  
}
