package com.example.shoprunner_be.services.Product;

import com.example.shoprunner_be.dtos.Product.ProductDTO;
import com.example.shoprunner_be.entitys.Product.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product getProductById(Long ProductId);

    List<Product> getProductByCategoryId(Long categoryId);

    Product addProduct(ProductDTO productDTO);

    Product updateProduct(Long ProductId, ProductDTO productDTO);

    void updateFavorite(Long productId);

    void deleteFavorite(Long productId);

    void deleteProduct(Long productId);
}
