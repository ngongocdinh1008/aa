package com.example.shoprunner_be.repositories;

import com.example.shoprunner_be.entitys.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {

    List<Product> findByCategory_Id(Long categoryId);

    @Modifying
    @Query("UPDATE Product p SET p.isFavorite = TRUE where p.id = :productId")
    void updateFavorite(@Param("productId") Long productId);

    @Modifying
    @Query("UPDATE Product p SET p.isFavorite = FALSE where p.id = :productId")
    void deleteFavorite(@Param("productId") Long productId);

}
