package com.rafdev.springboot.restful.api.demo.services;

import com.rafdev.springboot.restful.api.demo.entities.Category;

import java.util.List;

public interface CategoryService
{
    Category saveCategory(Category category);

    Category updateCategoryById(Long id, Category category);

    List<Category> getCategoriesList();

    Category getCategoryById(Long id);

    void deleteCategoryById(Long id);
}
