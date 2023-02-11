/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceapi.controller.goods;

import com.nixsolution.alextuleninov.marketplace.marketplaceapi.data.goods.GoodsResponse;
import com.nixsolution.alextuleninov.marketplace.marketplacelib.data.goods.SaveGoodsRequest;
import com.nixsolution.alextuleninov.marketplace.marketplaceapi.service.goods.GoodsCRUD;
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

import static com.nixsolution.alextuleninov.marketplace.marketplaceapi.exception.goods.GoodsExceptions.goodsNotFound;

/**
 * Rest controller for the Goods.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@RestController
public class GoodsController {

    private final GoodsCRUD goodsCRUD;

    public GoodsController(GoodsCRUD goodsCRUD) {
        this.goodsCRUD = goodsCRUD;
    }

    /**
     * Create the goods in the database.
     *
     * @param request       request with goods parameters
     * @param ucb           builder for UriComponents
     * @return              the goods from database in response format
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/admins/goods", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GoodsResponse> create(@Valid @RequestBody SaveGoodsRequest request, UriComponentsBuilder ucb) {
        GoodsResponse response = goodsCRUD.create(request);
        return ResponseEntity
                .created(ucb.path("/goods/{id}").build(response.id()))
                .body(response);
    }

    /**
     * Get all goods from database in response format with pagination information.
     *
     * @param pageable      abstract interface for pagination information
     * @return              all goods from database in response format
     */
    @GetMapping(value = "/goods", produces = MediaType.APPLICATION_JSON_VALUE)
    @PageableAsQueryParam
    public Page<GoodsResponse> getAll(@Parameter(hidden = true) Pageable pageable) {
        return goodsCRUD.findAll(pageable);
    }

    /**
     * Get all goods by category id from database in response format with pagination information.
     *
     * @param pageable      abstract interface for pagination information
     * @param id            id of category
     * @return              all goods from database in response format
     */
    @GetMapping(value = "/goods/categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<GoodsResponse> getAllByCategory(@Parameter(hidden = true) Pageable pageable, @PathVariable int id) {
        return goodsCRUD.findAllByCategory(pageable, id);
    }

    /**
     * Get the goods by id from the database in response format.
     *
     * @param id            id of goods
     * @return              the goods from database in response format
     */
    @GetMapping(value = "/goods/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GoodsResponse getById(@PathVariable int id) {
        return goodsCRUD.findById(id)
                .orElseThrow(() -> goodsNotFound(id));
    }

    /**
     * Update the goods in the database.
     *
     * @param id            id of goods
     * @param request       request with goods parameters
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/admins/goods/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable int id, @Valid @RequestBody SaveGoodsRequest request) {
        goodsCRUD.update(id, request);
    }

    /**
     * Delete the goods in the database.
     *
     * @param id            id of goods
     * @return              the goods from database in response format
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/admins/goods/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GoodsResponse delete(@PathVariable int id) {
        return goodsCRUD.deleteById(id)
                .orElseThrow(() -> goodsNotFound(id));
    }
}
