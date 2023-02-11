/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceweb.service.goods;

import com.nixsolution.alextuleninov.marketplace.marketplaceweb.feignclient.GoodsServiceFeignClient;
import com.nixsolution.alextuleninov.marketplace.marketplaceweb.model.goods.GoodsDTO;
import com.nixsolution.alextuleninov.marketplace.marketplaceweb.model.goods.GoodsUI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service class for Goods.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Service
public class GoodsService {

    private final GoodsServiceFeignClient goodsServiceFeignClient;

    public GoodsService(GoodsServiceFeignClient goodsServiceFeignClient) {
        this.goodsServiceFeignClient = goodsServiceFeignClient;
    }

    /**
     * Create the goods in the database.
     *
     * @param request       request with goods parameters
     */
    public void create(GoodsDTO request) {
        goodsServiceFeignClient.create(request);
    }

    /**
     * Find all goods from database in response format with pagination information.
     *
     * @param pageable      abstract interface for pagination information
     * @return              all goods from database in response format
     */
    public Page<GoodsUI> findAll(Pageable pageable) {
        return goodsServiceFeignClient.getAll(pageable)
                .map(GoodsUI::fromGoodsResponse);
    }

    /**
     * Find all goods by category id from database in response format with pagination information.
     *
     * @param pageable      abstract interface for pagination information
     * @param id            id of category
     * @return              all goods from database  in response format
     */
    public Page<GoodsUI> findAllByCategoryId(Pageable pageable, int id) {
        return goodsServiceFeignClient.getAllByCategory(pageable, id)
                .map(GoodsUI::fromGoodsResponse);
    }

    /**
     * Find the goods by id from the database in response format.
     *
     * @param id            id of goods
     * @return              the goods from database  in response format
     */
    public GoodsUI findById(int id) {
        return GoodsUI.fromGoodsResponse(
                goodsServiceFeignClient.getById(id));
    }

    /**
     * Update the goods in the database.
     *
     * @param id            id of goods
     * @param request       request with goods parameters
     */
    public void update(int id, GoodsDTO request) {
        goodsServiceFeignClient.update(id, request);
    }

    /**
     * Delete the goods in the database.
     *
     * @param id            id of goods
     * @return              the goods from database in response format
     */
    public GoodsUI delete(int id) {
        return GoodsUI.fromGoodsResponse(
                goodsServiceFeignClient.delete(id));
    }
}
