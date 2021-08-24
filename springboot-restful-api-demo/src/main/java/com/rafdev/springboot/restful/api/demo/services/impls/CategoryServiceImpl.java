package com.rafdev.springboot.restful.api.demo.services.impls;

import com.rafdev.springboot.restful.api.demo.entities.Category;
import com.rafdev.springboot.restful.api.demo.exceptions.ResourceNotFoundException;
import com.rafdev.springboot.restful.api.demo.repositries.CategoryRepository;
import com.rafdev.springboot.restful.api.demo.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService
{
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        super();
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategoryById(Long id, Category category) {
        Category categoryToUpdate = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", id));

        categoryToUpdate.setName(category.getName());

        return categoryRepository.save(categoryToUpdate);
    }

    @Override
    public List<Category> getCategoriesList() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", id));
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", id));

        categoryRepository.deleteById(id);
    }
}
