package com.olxseller.olx.serviceImp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olxseller.olx.DTO.ProductDTO;
import com.olxseller.olx.model.Product;
import com.olxseller.olx.model.User;
import com.olxseller.olx.repository.ProductRepository;
import com.olxseller.olx.repository.UserRepository;
import com.olxseller.olx.service.ProductService;
@Service
public class ProductServiceImpl implements ProductService{

  @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        // User user = userRepository.findById(productDTO.getUserId())
        // .orElseThrow(() -> new RuntimeException("User not found with id: " + productDTO.getUserId()));
        // product.setUser(user);
        // Product product = convertToEntity(productDTO);
        // product = productRepository.save(product);
        return convertToDTO(productRepository.save(convertToEntity(productDTO)));
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) {
        if (productRepository.existsById(productDTO.getId())) {
            Product product = productRepository.findById(productDTO.getId()).get();
            BeanUtils.copyProperties(productDTO, product, "id","createdAt","updatedAt","userId");
            // Product product = convertToEntity(productDTO);
            // product = productRepository.save(product);
            return convertToDTO(productRepository.save(product));
        } else {
            throw new RuntimeException("Product not found with id: " + productDTO.getId());
        }
    }

    @Override
    public void deleteProduct(int id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDTO> getProductById(int id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(this::convertToDTO);
    }

    private ProductDTO convertToDTO(Product product) { 
        ProductDTO productDTO = new ProductDTO();
        // productDTO.setId(product.getId());
        // productDTO.setName(product.getName());
        // productDTO.setDescription(product.getDescription());
        // productDTO.setPrice(product.getPrice());
        // productDTO.setQuantity(product.getQuantity());
        // productDTO.setImage(product.getImage());
        // productDTO.setCategory(product.getCategory());
        BeanUtils.copyProperties(product, productDTO);
        productDTO.setUserId(product.getUser().getId());
        return productDTO;
    }

    private Product convertToEntity(ProductDTO productDTO) { 
        Product product = new Product();
        // product.setId(productDTO.getId());
        // product.setName(productDTO.getName());
        // product.setDescription(productDTO.getDescription());
        // product.setPrice(productDTO.getPrice());
        // product.setQuantity(productDTO.getQuantity());
        // product.setImage(productDTO.getImage());
        // product.setCategory(productDTO.getCategory());
        BeanUtils.copyProperties(productDTO, product);
        User user = userRepository.findById(productDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + productDTO.getUserId()));
        product.setUser(user); 
        return product;
    } 

    @Override
    public ProductDTO updatePriceAndQty(ProductDTO productDTO) {
        // if (productRepository.existsById(productDTO.getId())) { 
        //     Product product = productRepository.findById(productDTO.getId()).get();
        //     product.setPrice(productDTO.getPrice());
        //     product.setQuantity(productDTO.getQuantity());
        //     product = productRepository.save(product);
        //     return convertToDTO(product);
        // } else {
        //     throw new RuntimeException("Product not found with id: " + productDTO.getId());
        // }  
        return convertToDTO(productRepository.save(productRepository.findById(productDTO.getId())
                    .map(product -> {
                        product.setPrice(productDTO.getPrice());
                        product.setQuantity(productDTO.getQuantity());
                        return product;
                    })
                    .orElseThrow(() -> new RuntimeException("Product not found with ID: "+productDTO.getId()))));

    }

    @Override
    public List<ProductDTO> searchProductsByTxt(String txt) {
        
        return productRepository.searchProducts(txt).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
  
}
