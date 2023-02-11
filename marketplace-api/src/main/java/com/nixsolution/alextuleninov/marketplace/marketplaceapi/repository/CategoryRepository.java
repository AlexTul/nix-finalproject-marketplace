/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceapi.repository;

import com.nixsolution.alextuleninov.marketplace.marketplaceapi.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for working with the repository of Category.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}