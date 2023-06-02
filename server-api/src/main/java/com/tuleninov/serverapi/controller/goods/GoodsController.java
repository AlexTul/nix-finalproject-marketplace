package com.tuleninov.serverapi.controller.goods;

import com.tuleninov.serverapi.Routes;
import com.tuleninov.serverapi.model.goods.request.SaveGoodsRequest;
import com.tuleninov.serverapi.model.goods.response.GoodsResponse;
import com.tuleninov.serverapi.service.goods.GoodsOperations;
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

import static com.tuleninov.serverapi.exceptions.goods.GoodsExceptions.goodsNotFound;

/**
 * Rest controller for the Goods.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@RestController
@RequestMapping(Routes.GOODS)
public class GoodsController {

    private final GoodsOperations goodsOperations;

    public GoodsController(GoodsOperations goodsOperations) {
        this.goodsOperations = goodsOperations;
    }

    /**
     * Create the goods in the database.
     *
     * @param request request with goods parameters
     * @param ucb     builder for UriComponents
     * @return the goods from the database in response format
     */
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GoodsResponse> create(@RequestBody @Valid SaveGoodsRequest request,
                                                UriComponentsBuilder ucb) {
        GoodsResponse response = goodsOperations.create(request);
        return ResponseEntity
                .created(ucb.path(Routes.GOODS + "/{id}").build(response.id()))
                .body(response);
    }

    /**
     * Find all goods in the database in response format with pagination information.
     *
     * @param pageable abstract interface for pagination information
     * @return all goods from the database in response format
     */
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PageableAsQueryParam
    public Page<GoodsResponse> listGoods(@Parameter(hidden = true) Pageable pageable) {
        return goodsOperations.list(pageable);
    }

    /**
     * Find all goods by category id in the database in response format with pagination information.
     *
     * @param pageable abstract interface for pagination information
     * @param id       the id of the category
     * @return all goods from the database in response format
     */
    @GetMapping(
            value = "/{id}/goods",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Page<GoodsResponse> listByCategoryId(@Parameter(hidden = true) Pageable pageable,
                                                @PathVariable int id) {
        return goodsOperations.findAllByCategoryId(pageable, id);
    }

    /**
     * Get the goods by id in the database in response format.
     *
     * @param id the id of the goods
     * @return the goods from database in response format
     */
    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GoodsResponse getGoodsById(@PathVariable int id) {
        return goodsOperations.findById(id)
                .orElseThrow(() -> goodsNotFound(id));
    }

    /**
     * Merge the goods by id in the database.
     *
     * @param id      the id of the goods
     * @param request the request with goods parameters
     */
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public GoodsResponse mergeGoodsById(@PathVariable int id,
                                        @RequestBody @Valid SaveGoodsRequest request) {
        return goodsOperations.mergeById(id, request);
    }

    /**
     * Delete the goods by id in the database.
     *
     * @param id the id of the goods
     * @return the goods from the database in response format
     */
    @DeleteMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public GoodsResponse deleteGoodsById(@PathVariable int id) {
        return goodsOperations.deleteById(id)
                .orElseThrow(() -> goodsNotFound(id));
    }
}
