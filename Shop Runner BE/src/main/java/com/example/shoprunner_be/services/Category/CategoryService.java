package com.example.shoprunner_be.services.Category;

import com.example.shoprunner_be.entitys.Category;

import java.util.List;

public interface CategoryService {

    Category getCategoryById(Long id);

    List<Category> getAllCategory();

}
