/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceapi.service.goods;

import com.nixsolution.alextuleninov.marketplace.marketplaceapi.data.goods.GoodsResponse;
import com.nixsolution.alextuleninov.marketplace.marketplacelib.data.goods.SaveGoodsRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Interface CRUD for Goods.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public interface GoodsCRUD {

    /**
     * Create the goods in the database.
     *
     * @param request       request with goods parameters
     * @return              the goods from database in response format
     */
    GoodsResponse create(SaveGoodsRequest request);

    /**
     * Find all goods from database in response format with pagination information.
     *
     * @param pageable      abstract interface for pagination information
     * @return              all goods from database in response format
     */
    Page<GoodsResponse> findAll(Pageable pageable);

    /**
     * Find all goods by category id from database in response format with pagination information.
     *
     * @param pageable      abstract interface for pagination information
     * @param id            id of category
     * @return              all goods from database  in response format
     */
    Page<GoodsResponse> findAllByCategory(Pageable pageable, int id);

    /**
     * Find the goods by id from the database in response format.
     *
     * @param id            id of goods
     * @return              the goods from database  in response format
     */
    Optional<GoodsResponse> findById(int id);

    /**
     * Update the goods in the database.
     *
     * @param id            id of goods
     * @param request       request with goods parameters
     */
    void update(int id, SaveGoodsRequest request);

    /**
     * Delete the goods in the database.
     *
     * @param id            id of goods
     * @return              the goods from database in response format
     */
    Optional<GoodsResponse> deleteById(int id);
}
