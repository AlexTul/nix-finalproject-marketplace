package com.tuleninov.serverapi.service.category;

import com.tuleninov.serverapi.exceptions.category.CategoryExceptions;
import com.tuleninov.serverapi.model.category.Category;
import com.tuleninov.serverapi.model.category.request.SaveCategoryRequest;
import com.tuleninov.serverapi.model.category.response.CategoryResponse;
import com.tuleninov.serverapi.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class for Category.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Service
public class CategoryService implements CategoryOperations {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Create the category in the database.
     *
     * @param request request with category parameters
     * @return the category from database in response format
     */
    @Override
    @Transactional
    public CategoryResponse create(SaveCategoryRequest request) {
        validateUniqueFields(request);
        return CategoryResponse.fromCategory(save(request));
    }

    /**
     * Find all categories from database in response format with pagination information.
     *
     * @param pageable abstract interface for pagination information
     * @return all categories from database in response format
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CategoryResponse> list(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(CategoryResponse::fromCategory);
    }

    /**
     * Find all categories from database in response format.
     *
     * @return all categories from database in response format
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> categoriesList() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponse::fromCategory)
                .toList();
    }

    /**
     * Find the category by id in the database in response format.
     *
     * @param id id of category
     * @return the category from database in response format
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryResponse> findById(int id) {
        return categoryRepository.findById(id)
                .map(CategoryResponse::fromCategory);
    }

    /**
     * Merge the category by id in the database.
     *
     * @param id      id of category
     * @param request request with category parameters
     * @return the category that was merged in response format
     */
    @Override
    @Transactional
    public CategoryResponse mergeById(int id, SaveCategoryRequest request) {
        Category category = getCategory(id);
        return CategoryResponse.fromCategory(merge(category, request));
    }

    /**
     * Delete the category by id in the database.
     *
     * @param id id of category
     * @return the category what was deleted in response format
     */
    @Override
    @Transactional
    public Optional<CategoryResponse> deleteById(int id) {
        if (!categoryRepository.existsById(id)) throw CategoryExceptions.categoryNotFound(id);

        Optional<Category> category = categoryRepository.findById(id);
        category.ifPresent(categoryRepository::delete);
        return category.map(CategoryResponse::fromCategory);
    }

    /**
     * Validate category`s fields.
     *
     * @param request request with category`s fields
     */
    private void validateUniqueFields(SaveCategoryRequest request) {
        String name = request.name();
        if (categoryRepository.existsByName(name)) {
            throw CategoryExceptions.duplicateName(name);
        }
    }

    /**
     * Save the category in the database.
     *
     * @param request request with user`s fields
     * @return the saved category
     */
    private Category save(SaveCategoryRequest request) {
        var category = new Category();
        category.setName(request.name());
        categoryRepository.save(category);
        return category;
    }

    /**
     * Get the category by id in the database.
     *
     * @param id the id of the category
     * @return the category with the given id
     */
    private Category getCategory(int id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> CategoryExceptions.categoryNotFound(id));
    }

    /**
     * Merge the category in the database.
     *
     * @param category the category from the database
     * @param request  request with category`s data
     * @return the category that was merged
     */
    private Category merge(Category category, SaveCategoryRequest request) {
        String name = request.name();
        if (name != null && !name.equals(category.getName())) {
            if (categoryRepository.existsByName(name)) throw CategoryExceptions.duplicateName(name);
            category.setName(name);
        }
        return category;
    }
}
