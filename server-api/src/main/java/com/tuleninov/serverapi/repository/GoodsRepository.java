package com.tuleninov.serverapi.repository;

import com.tuleninov.serverapi.model.category.Category;
import com.tuleninov.serverapi.model.goods.Goods;
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

    boolean existsByName(String name);

    boolean existsByDescription(String description);

    boolean existsByImageName(String imageName);

}
