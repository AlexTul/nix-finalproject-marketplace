/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceapi.service.goods;

import com.nixsolution.alextuleninov.marketplace.marketplaceapi.data.goods.GoodsResponse;
import com.nixsolution.alextuleninov.marketplace.marketplacelib.data.goods.SaveGoodsRequest;
import com.nixsolution.alextuleninov.marketplace.marketplaceapi.model.category.Category;
import com.nixsolution.alextuleninov.marketplace.marketplaceapi.model.goods.Goods;
import com.nixsolution.alextuleninov.marketplace.marketplaceapi.repository.CategoryRepository;
import com.nixsolution.alextuleninov.marketplace.marketplaceapi.repository.GoodsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.nixsolution.alextuleninov.marketplace.marketplaceapi.exception.category.CategoryExceptions.categoryNotFound;
import static com.nixsolution.alextuleninov.marketplace.marketplaceapi.exception.goods.GoodsExceptions.goodsNotFound;

/**
 * Service class for Goods.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Service
public class GoodsService implements GoodsCRUD {

    private final GoodsRepository goodsRepository;

    private final CategoryRepository categoryRepository;

    public GoodsService(GoodsRepository goodsRepository, CategoryRepository categoryRepository) {
        this.goodsRepository = goodsRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Create the goods in the database.
     *
     * @param request       request with goods parameters
     * @return              the goods from database in response format
     */
    @Override
    @Transactional
    public GoodsResponse create(SaveGoodsRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> categoryNotFound(request.categoryId()));

        var goods = new Goods(
                request.name(),
                category,
                request.price(),
                request.weight(),
                request.description(),
                request.imageName()
        );
        return GoodsResponse.fromGoods(goodsRepository.save(goods));
    }

    /**
     * Find all goods from database in response format with pagination information.
     *
     * @param pageable      abstract interface for pagination information
     * @return              all goods from database in response format
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GoodsResponse> findAll(Pageable pageable) {
        return goodsRepository.findAll(pageable)
                .map(GoodsResponse::fromGoods);
    }

    /**
     * Find all goods by category id from database in response format with pagination information.
     *
     * @param pageable      abstract interface for pagination information
     * @param id            id of category
     * @return              all goods from database  in response format
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GoodsResponse> findAllByCategory(Pageable pageable, int id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> categoryNotFound(id));

        return goodsRepository.findAllByCategory(pageable, category)
                .map(GoodsResponse::fromGoods);
    }

    /**
     * Find the goods by id from the database in response format.
     *
     * @param id            id of goods
     * @return              the goods from database  in response format
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GoodsResponse> findById(int id) {
        return goodsRepository.findById(id)
                .map(GoodsResponse::fromGoods);
    }

    /**
     * Update the goods in the database.
     *
     * @param id            id of goods
     * @param request       request with goods parameters
     */
    @Override
    @Transactional
    public void update(int id, SaveGoodsRequest request) {
        Goods goods = goodsRepository.findById(id)
                .orElseThrow(() -> goodsNotFound(id));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> categoryNotFound(request.categoryId()));

        goods.setName(request.name());
        goods.setCategory(category);
        goods.setPrice(request.price());
        goods.setWeight(request.weight());
        goods.setDescription(request.description());
        goods.setImageName(request.imageName());
    }

    /**
     * Delete the goods in the database.
     *
     * @param id            id of goods
     * @return              the goods from database in response format
     */
    @Override
    @Transactional
    public Optional<GoodsResponse> deleteById(int id) {
        Optional<Goods> goods = goodsRepository.findById(id);
        goods.ifPresent(goodsRepository::delete);

        return goods.map(GoodsResponse::fromGoods);
    }
}
