package com.tuleninov.web.service.category;

import com.tuleninov.web.feignclient.CategoryServiceFeignClient;
import com.tuleninov.web.model.category.CategoryUI;
import com.tuleninov.web.model.category.request.SaveCategoryUIRequest;
import com.tuleninov.web.model.category.response.CategoryUIResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for Category.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Service
public class CategoryService {

    private final CategoryServiceFeignClient categoryServiceFeignClient;

    public CategoryService(CategoryServiceFeignClient categoryServiceFeignClient) {
        this.categoryServiceFeignClient = categoryServiceFeignClient;
    }

    /**
     * Create the category in the database.
     *
     * @param token   token to access the corresponding endpoint
     * @param request request with category parameters
     * @return the category from database in response format
     */
    public CategoryUI create(String token, SaveCategoryUIRequest request) {
        return CategoryUIResponse.fromCategoryUIResponse(
                categoryServiceFeignClient.create(token, request));
    }

    /**
     * Find all categories from database in response format with pagination information.
     *
     * @param token    token to access the corresponding endpoint
     * @param pageable abstract interface for pagination information
     * @return all categories from database in response format
     */
    public Page<CategoryUI> listCategories(String token, Pageable pageable) {
        return categoryServiceFeignClient.listCategories(token, pageable)
                .map(CategoryUI::fromCategoryResponse);
    }

    /**
     * Get all categories from the database.
     *
     * @return all categories from database
     */
    public List<CategoryUI> getListCategories() {
        return categoryServiceFeignClient.getListCategories()
                .stream()
                .map(CategoryUI::fromCategoryResponse).toList();
    }

    /**
     * Get the category by id from the database.
     *
     * @param token token to access the corresponding endpoint
     * @param id    id of the category
     * @return the category from database
     */
    public CategoryUI getCategoryById(String token, int id) {
        return CategoryUI.fromCategoryResponse(
                categoryServiceFeignClient.getCategoryById(token, id));
    }

    /**
     * Merge category in the database.
     *
     * @param token   token to access the corresponding endpoint
     * @param id      id of the category
     * @param request request with category parameters
     */
    public CategoryUI mergeCategoryById(String token, int id, SaveCategoryUIRequest request) {
        return CategoryUI.fromCategoryResponse(
                categoryServiceFeignClient.mergeCategoryById(token, id, request));
    }

    /**
     * Delete the category in the database.
     *
     * @param token token to access the corresponding endpoint
     * @param id    id of the category
     */
    public CategoryUI deleteCategoryById(String token, int id) {
        return CategoryUI.fromCategoryResponse(
                categoryServiceFeignClient.deleteCategoryById(token, id));
    }
}
