package com.example.shoprunner_be.services.Category;

import com.example.shoprunner_be.entitys.Category;
import com.example.shoprunner_be.exceptions.EntityNotFoundException;
import com.example.shoprunner_be.repositories.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryImp implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepo
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id " + id));
    }

    @Override
    public List<Category> getAllCategory() {
        List<Category> categories = categoryRepo.findAll();
        if (categories.isEmpty()) {
            throw new EntityNotFoundException("Category not found !!");
        }
        return categories;
    }
}
