package com.example.shoprunner_be.repositories;

import com.example.shoprunner_be.entitys.Product.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepo extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductId(Long productId);
}
