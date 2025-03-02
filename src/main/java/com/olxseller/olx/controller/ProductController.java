package com.olxseller.olx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.olxseller.olx.DTO.ProductDTO;
import com.olxseller.olx.service.ProductService;

@RestController
@RequestMapping("/product")
@Validated
public class ProductController {
  @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Validated ProductDTO productDTO) { 
        ProductDTO savedProduct = productService.saveProduct(productDTO);
        return ResponseEntity.ok(savedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable int id, @RequestBody @Validated ProductDTO productDTO) {
        productDTO.setId(id); 
        ProductDTO updatedProduct = productService.updateProduct(productDTO);
        return ResponseEntity.ok(updatedProduct);
    }
    @PutMapping("/price/{id}")
    public ResponseEntity<ProductDTO> updateProductPriceQty(@PathVariable int id, @RequestBody @Validated ProductDTO productDTO) {
        productDTO.setId(id); 
        ProductDTO updatedProduct = productService.updatePriceAndQty(productDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/items")
    public Page<ProductDTO> srollingProduct(@RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "2") int size,@RequestParam(defaultValue = "0") int userId) {
        Page<ProductDTO> products = productService.productScrolling(page,size,userId);
        return products;
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProducts(@RequestParam String s) {
        List<ProductDTO> products = productService.searchProductsByTxt(s);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable int id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
