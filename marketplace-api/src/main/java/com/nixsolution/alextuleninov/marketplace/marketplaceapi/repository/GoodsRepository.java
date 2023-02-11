/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceapi.repository;

import com.nixsolution.alextuleninov.marketplace.marketplaceapi.model.category.Category;
import com.nixsolution.alextuleninov.marketplace.marketplaceapi.model.goods.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for working with the repository of Goods.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public interface GoodsRepository extends JpaRepository<Goods, Integer> {

    Page<Goods> findAllByCategory(Pageable pageable, Category category);
}
