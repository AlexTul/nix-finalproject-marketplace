package com.tuleninov.serverapi.service.category;

import com.tuleninov.serverapi.model.category.request.SaveCategoryRequest;
import com.tuleninov.serverapi.model.category.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Interface CRUD for Category.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public interface CategoryOperations {

    /**
     * Create the category in the database.
     *
     * @param request       request with category parameters
     * @return              the category from database in response format
     */
    CategoryResponse create(SaveCategoryRequest request);

    /**
     * Find all categories from database in response format with pagination information.
     *
     * @param pageable      abstract interface for pagination information
     * @return              all categories from database in response format
     */
    Page<CategoryResponse> list(Pageable pageable);

    /**
     * Find all categories from database in response format.
     *
     * @return              all categories from database in response format
     */
    List<CategoryResponse> categoriesList();

    /**
     * Find the category by id from the database in response format.
     *
     * @param id            id of category
     * @return              the category from database in response format
     */
    Optional<CategoryResponse> findById(int id);

    /**
     * Update the category in the database.
     *
     * @param id            id of category
     * @param request       request with category parameters
     */
    CategoryResponse mergeById(int id, SaveCategoryRequest request);

    /**
     * Delete the category in the database.
     *
     * @param id            id of category
     */
    Optional<CategoryResponse> deleteById(int id);
}
