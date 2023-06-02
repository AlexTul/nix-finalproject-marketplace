package com.tuleninov.serverapi.service.goods;

import com.tuleninov.serverapi.exceptions.category.CategoryExceptions;
import com.tuleninov.serverapi.exceptions.goods.GoodsExceptions;
import com.tuleninov.serverapi.model.category.Category;
import com.tuleninov.serverapi.model.goods.Goods;
import com.tuleninov.serverapi.model.goods.request.SaveGoodsRequest;
import com.tuleninov.serverapi.model.goods.response.GoodsResponse;
import com.tuleninov.serverapi.repository.CategoryRepository;
import com.tuleninov.serverapi.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.tuleninov.serverapi.exceptions.category.CategoryExceptions.categoryNotFound;

/**
 * Service class for Goods.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Service
public class GoodsService implements GoodsOperations {

    private final GoodsRepository goodsRepository;

    private final CategoryRepository categoryRepository;

    public GoodsService(GoodsRepository goodsRepository, CategoryRepository categoryRepository) {
        this.goodsRepository = goodsRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Create the goods in the database.
     *
     * @param request request with goods parameters
     * @return the goods from database in response format
     */
    @Override
    @Transactional
    public GoodsResponse create(SaveGoodsRequest request) {
        validateUniqueFields(request);
        return GoodsResponse.fromGoods(save(request));
    }

    /**
     * Find all goods in the database in response format with pagination information.
     *
     * @param pageable abstract interface for pagination information
     * @return all goods from the database in response format
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GoodsResponse> list(Pageable pageable) {
        return goodsRepository.findAll(pageable)
                .map(GoodsResponse::fromGoods);
    }

    /**
     * Find all goods by category id in the database in response format with pagination information.
     *
     * @param pageable abstract interface for pagination information
     * @param id       the id of the category
     * @return all goods from the database in response format
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GoodsResponse> findAllByCategoryId(Pageable pageable, int id) {
        Category category = getCategory(id);

        return goodsRepository.findAllByCategory(pageable, category)
                .map(GoodsResponse::fromGoods);
    }

    /**
     * Find the goods by id in the database in response format.
     *
     * @param id the id of the goods
     * @return the goods from database in response format
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GoodsResponse> findById(int id) {
        return goodsRepository.findById(id)
                .map(GoodsResponse::fromGoods);
    }

    /**
     * Merge the goods in the database.
     *
     * @param id      the id of the goods
     * @param request the request with goods parameters
     */
    @Override
    @Transactional
    public GoodsResponse mergeById(int id, SaveGoodsRequest request) {
        Goods goods = getGoods(id);
        return GoodsResponse.fromGoods(merge(goods, request));
    }

    /**
     * Delete the goods in the database.
     *
     * @param id the id of the goods
     * @return the goods from the database in response format
     */
    @Override
    @Transactional
    public Optional<GoodsResponse> deleteById(int id) {
        if (!goodsRepository.existsById(id)) throw GoodsExceptions.goodsNotFound(id);

        Optional<Goods> goods = goodsRepository.findById(id);
        goods.ifPresent(goodsRepository::delete);
        return goods.map(GoodsResponse::fromGoods);
    }

    /**
     * Validate goods`s fields.
     *
     * @param request request with goods`s fields
     */
    private void validateUniqueFields(SaveGoodsRequest request) {
        String name = request.name();
        if (goodsRepository.existsByName(name)) {
            throw GoodsExceptions.duplicateName(name);
        }
        String imageName = request.imageName();
        if (goodsRepository.existsByImageName(imageName)) {
            throw GoodsExceptions.duplicateImageName(imageName);
        }
    }

    /**
     * Save the goods in the database.
     *
     * @param request request with user`s fields
     * @return the saved goods
     */
    private Goods save(SaveGoodsRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> categoryNotFound(request.categoryId()));

        var goods = new Goods();
        goods.setName(request.name());
        goods.setCategory(category);
        goods.setPrice(request.price());
        goods.setWeight(request.weight());
        goods.setDescription(request.description());
        goods.setImageName(request.imageName());
        goodsRepository.save(goods);
        return goods;
    }

    /**
     * Get the category by id in the database.
     *
     * @param id the id of the category
     * @return the category with the given id
     */
    private Category getCategory(int id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> CategoryExceptions.categoryNotFound(id));
    }

    /**
     * Get the goods by id in the database.
     *
     * @param id the id of the goods
     * @return the category with the given id
     */
    private Goods getGoods(int id) {
        return goodsRepository.findById(id)
                .orElseThrow(() -> GoodsExceptions.goodsNotFound(id));
    }

    /**
     * Merge the goods in the database.
     *
     * @param goods   the goods from the database
     * @param request request with goods`s data
     * @return the goods that was merged
     */
    private Goods merge(Goods goods, SaveGoodsRequest request) {
        String name = request.name();
        if (name != null && !name.equals(goods.getName())) {
            if (goodsRepository.existsByName(name)) throw GoodsExceptions.duplicateName(name);
            goods.setName(name);
        }
        Category category = getCategory(request.categoryId());
        if (category != null && !category.equals(goods.getCategory())) {
            goods.setCategory(category);
        }
        double price = request.price();
        if (price > 0 && !(price == goods.getPrice())) {
            goods.setPrice(request.price());
        }
        int weight = request.weight();
        if (weight > 0 && !(weight == goods.getWeight())) {
            goods.setWeight(request.weight());
        }
        String description = request.description();
        if (description != null && !description.equals(goods.getDescription())) {
            if (goodsRepository.existsByDescription(description))
                throw GoodsExceptions.duplicateDescription(description);
            goods.setDescription(request.description());
        }
        String imageName = request.imageName();
        if (imageName != null && !imageName.equals(goods.getImageName())) {
            if (goodsRepository.existsByImageName(imageName))
                throw GoodsExceptions.duplicateImageName(imageName);
            goods.setImageName(request.imageName());
        }

        return goods;
    }
}
