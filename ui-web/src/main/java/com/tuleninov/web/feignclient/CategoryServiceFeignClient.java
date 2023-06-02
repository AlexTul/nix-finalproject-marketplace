package com.tuleninov.web.feignclient;

import com.tuleninov.web.Routes;
import com.tuleninov.web.model.category.request.SaveCategoryUIRequest;
import com.tuleninov.web.model.category.response.CategoryUIResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.tuleninov.web.config.security.SecurityConstantsUI.AUTH_CLAIM;

/**
 * Feign Client for the Category.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@FeignClient(name = "CategoryController.class", url = "${services.server.api}")
public interface CategoryServiceFeignClient {

    /**
     * Create the category in the database.
     *
     * @param token   token to access the corresponding endpoint
     * @param request request with category parameters
     */
    @PostMapping(
            value = Routes.API_CATEGORIES,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    CategoryUIResponse create(@RequestHeader(AUTH_CLAIM) String token,
                              @RequestBody @Valid SaveCategoryUIRequest request);

    /**
     * Get all categories from the database in response format with pagination information.
     *
     * @param token   token to access the corresponding endpoint
     * @param pageable abstract interface for pagination information
     * @return all categories from the database in response format
     */
    @GetMapping(
            value = Routes.API_CATEGORIES,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Page<CategoryUIResponse> listCategories(@RequestHeader(AUTH_CLAIM) String token,
                                            Pageable pageable);

    /**
     * Get all categories from the database in response format.
     *
     * @return all categories from the database in response format
     */
    @GetMapping(
            value = Routes.API_CATEGORIES + "/list",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<CategoryUIResponse> getListCategories();

    /**
     * Get the category by id in the database in response format.
     *
     * @param token   token to access the corresponding endpoint
     * @param id id of category
     * @return the category from database in response format
     */
    @GetMapping(
            value = Routes.API_CATEGORIES + "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    CategoryUIResponse getCategoryById(@RequestHeader(AUTH_CLAIM) String token,
                                       @PathVariable int id);

    /**
     * Merge the category by id in the database.
     *
     * @param token   token to access the corresponding endpoint
     * @param id      id of category
     * @param request request with category parameters
     */
    @PutMapping(
            value = Routes.API_CATEGORIES + "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    CategoryUIResponse mergeCategoryById(@RequestHeader(AUTH_CLAIM) String token,
                                         @PathVariable int id,
                                         @RequestBody @Valid SaveCategoryUIRequest request);

    /**
     * Delete the category by id in the database.
     *
     * @param token token to access the corresponding endpoint
     * @param id    id of category
     */
    @DeleteMapping(
            value = Routes.API_CATEGORIES + "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    CategoryUIResponse deleteCategoryById(@RequestHeader(AUTH_CLAIM) String token,
                                          @PathVariable int id);
}
