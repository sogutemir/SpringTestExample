package org.work.springtestexample.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.work.springtestexample.dto.ProductDTO;
import org.work.springtestexample.model.Product;
import org.work.springtestexample.repository.ProductRepository;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());

        return productDTO;
    }

    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());

        product = productRepository.save(product);
        productDTO.setId(product.getId());
        return productDTO;
    }
}