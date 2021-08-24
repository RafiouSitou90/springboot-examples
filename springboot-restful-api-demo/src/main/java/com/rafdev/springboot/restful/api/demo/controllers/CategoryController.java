package com.rafdev.springboot.restful.api.demo.controllers;

import com.rafdev.springboot.restful.api.demo.entities.Category;
import com.rafdev.springboot.restful.api.demo.services.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController
{
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        super();
        this.categoryService = categoryService;
    }

    @Operation(summary = "Create a new category")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Category created successfully!",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Category.class)
                                    )
                            }
                    )
            }
    )
    @PostMapping("")
    public ResponseEntity<Category> saveCategory(@Valid @RequestBody Category category) {
        return new ResponseEntity<>(categoryService.saveCategory(category), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategoryById(@PathVariable("id") Long id,
                                                       @Valid @RequestBody Category category) {
        return new ResponseEntity<>(categoryService.updateCategoryById(id, category), HttpStatus.OK);
    }

    @GetMapping("")
    public List<Category> getCategoriesList() {
        return categoryService.getCategoriesList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(categoryService.getCategoryById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable("id") Long id) {
        categoryService.deleteCategoryById(id);

        return new ResponseEntity<>("Category deleted successfully!", HttpStatus.OK);
    }
}
