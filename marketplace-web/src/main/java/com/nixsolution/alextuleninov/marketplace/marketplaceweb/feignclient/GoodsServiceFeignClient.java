/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceweb.feignclient;

import com.nixsolution.alextuleninov.marketplace.marketplaceweb.data.goods.GoodsUIResponse;
import com.nixsolution.alextuleninov.marketplace.marketplaceweb.model.goods.GoodsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Feign Client for the Goods.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@FeignClient(name ="GoodsController.class", url = "${services.marketplace.api}")
public interface GoodsServiceFeignClient {

    /**
     * Create the goods in the database.
     *
     * @param request       request with goods parameters
     * @return              the goods from database in response format
     */
    @PostMapping(value = "/admins/goods", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    void create(@RequestBody GoodsDTO request);

    /**
     * Get all goods from database in response format with pagination information.
     *
     * @param pageable      abstract interface for pagination information
     * @return              all goods from database in response format
     */
    @GetMapping(value = "/goods", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<GoodsUIResponse> getAll(Pageable pageable);

    /**
     * Get all goods by category id from database in response format with pagination information.
     *
     * @param pageable      abstract interface for pagination information
     * @param id            id of category
     * @return              all goods from database in response format
     */
    @GetMapping(value = "/goods/categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<GoodsUIResponse> getAllByCategory(Pageable pageable, @PathVariable int id);

    /**
     * Get the goods by id from the database in response format.
     *
     * @param id            id of goods
     * @return              the goods from database in response format
     */
    @GetMapping(value = "/goods/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    GoodsUIResponse getById(@PathVariable int id);

    /**
     * Update the goods in the database.
     *
     * @param id            id of goods
     * @param request       request with goods parameters
     */
    @PutMapping(value = "/admins/goods/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    void update(@PathVariable int id, @RequestBody GoodsDTO request);

    /**
     * Delete the goods in the database.
     *
     * @param id            id of goods
     * @return              the goods from database in response format
     */
    @DeleteMapping(value = "/admins/goods/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    GoodsUIResponse delete(@PathVariable int id);
}
