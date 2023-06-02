package com.tuleninov.serverapi.repository;

import com.tuleninov.serverapi.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for working with the repository of Category.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    boolean existsByName(String name);

}