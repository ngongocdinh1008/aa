package com.example.shoprunner_be.repositories;

import com.example.shoprunner_be.entitys.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
//    boolean existsByName(String name);
}
