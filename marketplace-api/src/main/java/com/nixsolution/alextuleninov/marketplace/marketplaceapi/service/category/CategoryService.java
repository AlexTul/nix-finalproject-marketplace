/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceapi.service.category;

import com.nixsolution.alextuleninov.marketplace.marketplaceapi.model.category.Category;
import com.nixsolution.alextuleninov.marketplace.marketplaceapi.repository.CategoryRepository;
import com.nixsolution.alextuleninov.marketplace.marketplaceapi.data.category.CategoryResponse;
import com.nixsolution.alextuleninov.marketplace.marketplacelib.data.category.SaveCategoryRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.nixsolution.alextuleninov.marketplace.marketplaceapi.exception.category.CategoryExceptions.categoryNotFound;

/**
 * Service class for Category.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Service
public class CategoryService implements CategoryCRUD {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Create the category in the database.
     *
     * @param request       request with category parameters
     * @return              the category from database in response format
     */
    @Override
    @Transactional
    public CategoryResponse create(SaveCategoryRequest request) {
        var category = new Category(request.name());
        return CategoryResponse.fromCategory(categoryRepository.save(category));
    }

    /**
     * Find all categories from database in response format with pagination information.
     *
     * @param pageable      abstract interface for pagination information
     * @return              all categories from database in response format
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CategoryResponse> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(CategoryResponse::fromCategory);
    }

    /**
     * Find all categories from database in response format.
     *
     * @return              all categories from database in response format
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> findAllList() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponse::fromCategory).toList();
    }

    /**
     * Find the category by id from the database in response format.
     *
     * @param id            id of category
     * @return              the category from database in response format
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryResponse> findById(int id) {
        return categoryRepository.findById(id)
                .map(CategoryResponse::fromCategory);
    }

    /**
     * Update the category in the database.
     *
     * @param id            id of category
     * @param request       request with category parameters
     */
    @Override
    @Transactional
    public void update(int id, SaveCategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> categoryNotFound(id));
        category.setName(request.name());
    }

    /**
     * Delete the category in the database.
     *
     * @param id            id of category
     */
    @Override
    @Transactional
    public void deleteById(int id) {
        Optional<Category> category = categoryRepository.findById(id);
        category.ifPresent(categoryRepository::delete);
    }
}
