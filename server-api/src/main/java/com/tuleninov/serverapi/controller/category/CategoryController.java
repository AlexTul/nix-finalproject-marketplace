package com.tuleninov.serverapi.controller.category;

import com.tuleninov.serverapi.Routes;
import com.tuleninov.serverapi.model.category.request.SaveCategoryRequest;
import com.tuleninov.serverapi.model.category.response.CategoryResponse;
import com.tuleninov.serverapi.service.category.CategoryOperations;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

import static com.tuleninov.serverapi.exceptions.category.CategoryExceptions.categoryNotFound;

/**
 * Rest controller for the Category.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@RestController
@RequestMapping(Routes.CATEGORIES)
public class CategoryController {

    private final CategoryOperations categoryOperations;

    public CategoryController(CategoryOperations categoryOperations) {
        this.categoryOperations = categoryOperations;
    }

    /**
     * Create the category in the database.
     *
     * @param request request with category parameters
     * @param ucb     builder for UriComponents
     * @return category that was created from the database in response format
     */
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CategoryResponse> create(@RequestBody @Valid SaveCategoryRequest request,
                                                   UriComponentsBuilder ucb) {
        CategoryResponse response = categoryOperations.create(request);
        return ResponseEntity
                .created(ucb.path(Routes.CATEGORIES + "/{id}").build(response.id()))
                .body(response);
    }

    /**
     * Get all categories from the database in response format with pagination information.
     *
     * @param pageable abstract interface for pagination information
     * @return all categories from the database in response format
     */
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PageableAsQueryParam
    public Page<CategoryResponse> listCategories(@Parameter(hidden = true) Pageable pageable) {
        return categoryOperations.list(pageable);
    }

    /**
     * Get all categories from the database in response format.
     *
     * @return all categories from the database in response format
     */
    @GetMapping(
            value = "/list",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<CategoryResponse> getListCategories() {
        return categoryOperations.categoriesList();
    }

    /**
     * Get the category by id in the database in response format.
     *
     * @param id the id of the category
     * @return the category from database in response format
     */
    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CategoryResponse getCategoryById(@PathVariable int id) {
        return categoryOperations.findById(id)
                .orElseThrow(() -> categoryNotFound(id));
    }

    /**
     * Merge the category by id in the database.
     *
     * @param id      the id of the category
     * @param request request with category parameters
     */
    @PutMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public CategoryResponse mergeCategoryById(@PathVariable int id,
                                              @RequestBody @Valid SaveCategoryRequest request) {
        return categoryOperations.mergeById(id, request);
    }

    /**
     * Delete the category by id in the database.
     *
     * @param id the id of the category
     */
    @DeleteMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponse deleteCategoryById(@PathVariable int id) {
        return categoryOperations.deleteById(id)
                .orElseThrow(() -> categoryNotFound(id));
    }
}
