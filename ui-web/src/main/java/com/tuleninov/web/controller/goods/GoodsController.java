package com.tuleninov.web.controller.goods;

import com.tuleninov.web.Routes;
import com.tuleninov.web.config.mvc.file.FileProperties;
import com.tuleninov.web.config.pagination.ConfigDTO;
import com.tuleninov.web.config.pagination.PaginationConfig;
import com.tuleninov.web.controller.TokenProvider;
import com.tuleninov.web.controller.category.CategoryController;
import com.tuleninov.web.controller.exceptions.FileExceptions;
import com.tuleninov.web.io.remove.Remover;
import com.tuleninov.web.io.write.Writer;
import com.tuleninov.web.model.goods.GoodsUI;
import com.tuleninov.web.model.goods.request.SaveGoodsUIRequest;
import com.tuleninov.web.service.category.CategoryService;
import com.tuleninov.web.service.goods.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;

import static com.tuleninov.web.AppConstants.*;

/**
 * Controller for the goods.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Controller
@RequestMapping(Routes.WEB_GOODS)
public class GoodsController {

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    private final TokenProvider tokenProvider;
    private final CategoryService categoryService;
    private final GoodsService goodsService;
    private final FileProperties fileProperties;
    private final Writer writer;
    private final Remover remover;

    public GoodsController(TokenProvider tokenProvider, CategoryService categoryService,
                           GoodsService goodsService, FileProperties fileProperties, Writer writer, Remover remover) {
        this.tokenProvider = tokenProvider;
        this.categoryService = categoryService;
        this.goodsService = goodsService;
        this.fileProperties = fileProperties;
        this.writer = writer;
        this.remover = remover;
    }

    /**
     * Get goods`s page.
     *
     * @param req   the servlet container, which provide req information for HTTP servlets
     * @param model holder for model attributes
     * @return goods page
     */
    @GetMapping
    public String getGoodsPage(HttpServletRequest req,
                               Model model) {
        var config = PaginationConfig.config(req);
        var goods = getGoods(config);

        model.addAttribute(SCOPE_OBJECTS, goods);

        return "goods/goods";
    }

    /**
     * Get the goods create page with parameters.
     *
     * @param req   the servlet container, which provide req information for HTTP servlets
     * @param model holder for model attributes
     * @return goods`s create page
     */
    @GetMapping(value = "/create")
    public String getGoodsAddPage(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable,
                                  HttpServletRequest req,
                                  Model model) {
        var token = tokenProvider.provideTokenForHeader(req);
        var categories = categoryService.listCategories(token, pageable);

        model.addAttribute(SCOPE_GOODS, new SaveGoodsUIRequest());
        model.addAttribute(SCOPE_CATEGORIES, categories);

        return "goods/goods-create";
    }

    /**
     * Create the goods to the database.
     *
     * @param request request with goods parameters
     * @param file    a representation of an uploaded file received in a multipart request
     * @param req     the servlet container, which provide req information for HTTP servlets
     * @return goods page
     */
    @PostMapping
    public String createGoods(@Valid SaveGoodsUIRequest request,
                              @RequestParam("goodsImage") MultipartFile file,
                              HttpServletRequest req) {
        if (file.isEmpty()) throw FileExceptions.noFileNameSelected();

        request.setImageName(file.getOriginalFilename());

        if (createUploadDirectoryIfNotExists()) {
            log.info("Dir 'files' has been created.");
        }
        writer.write(fileProperties.getPath(), file);

        var token = tokenProvider.provideTokenForHeader(req);
        var goods = goodsService.create(token, request);

        log.info("Goods '" + goods.getName() + "' has been added to database.");

        return "redirect:" + Routes.WEB_GOODS;
    }

    /**
     * Get the merge goods`s page.
     *
     * @param pageable pagination configuration
     * @param id       the id of the goods
     * @param req      the servlet container, which provide req information for HTTP servlets
     * @param model    holder for model attributes
     * @return goods`s update page
     */
    @GetMapping("/{id}")
    public String getMergeGoodsPage(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable,
                                    @PathVariable(value = "id") int id,
                                    HttpServletRequest req,
                                    Model model) {
        var token = tokenProvider.provideTokenForHeader(req);
        var goods = goodsService.getGoodsById(id);

        model.addAttribute(SCOPE_GOODS, goods);
        model.addAttribute(SCOPE_CATEGORIES, categoryService.listCategories(token, pageable));

        return "goods/goods-merge";
    }

    /**
     * Merge the goods in the database.
     *
     * @param id      the id of the goods
     * @param request the request with goods parameters
     * @param file    a representation of an uploaded file received in a multipart request
     * @param req     the servlet container, which provide req information for HTTP servlets
     * @return goods page
     */
    @PatchMapping("/{id}")
    public String mergeGoodsById(@PathVariable(value = "id") int id,
                                 @Valid SaveGoodsUIRequest request,
                                 @RequestParam("goodsImage") MultipartFile file,
                                 HttpServletRequest req) {
        if (file.isEmpty()) throw FileExceptions.noFileNameSelected();

        request.setImageName(file.getOriginalFilename());
        var token = tokenProvider.provideTokenForHeader(req);
        fileOverwriteIfFileIsNew(id, request, file);
        var mergedGoods = goodsService.mergeGoodsById(token, id, request);

        log.info("Goods '" + mergedGoods.getName() + "' were merged.");

        return "redirect:" + Routes.WEB_GOODS;
    }

    /**
     * Delete the goods from the database.
     *
     * @param id  the id of the goods
     * @param req the servlet container, which provide req information for HTTP servlets
     * @return goods`s page
     */
    @DeleteMapping("/{id}")
    public String deleteGoods(@PathVariable(value = "id") int id,
                              HttpServletRequest req) {
        var token = tokenProvider.provideTokenForHeader(req);
        var goods = goodsService.deleteGoodsById(token, id);
        remover.remove(fileProperties.getPath(), goods);

        log.info("Goods '" + goods.getName() + "' was deleted.");

        return "redirect:" + Routes.WEB_GOODS;
    }

    /**
     * Create the upload directory if it doesn't exist.
     *
     * @return true if and only if the directory was created,
     * along with all necessary parent directories; false otherwise
     */
    private boolean createUploadDirectoryIfNotExists() {
        var uploadDir = new File(fileProperties.getPath());
        boolean created = false;
        if (!uploadDir.exists()) {
            created = uploadDir.mkdirs();
        }
        return created;
    }

    /**
     * Overwrite goods image file if attached file is new.
     *
     * @param id      the id of the goods
     * @param request the request with goods parameters
     * @param file    a representation of an uploaded file received in a multipart request
     */
    private void fileOverwriteIfFileIsNew(int id, SaveGoodsUIRequest request, MultipartFile file) {
        var goods = goodsService.getGoodsById(id);
        if (!request.getImageName().equals(goods.getImageName())) {
            remover.remove(fileProperties.getPath(), goods);
            writer.write(fileProperties.getPath(), file);
        }
    }

    /**
     * Get the goods from the database.
     * (If the user enters an incorrect page number, which is greater than the actual one,
     * into the address bar, it will redirect to the last valid page).
     *
     * @param config pagination config
     * @return goods
     */
    private Page<GoodsUI> getGoods(ConfigDTO config) {
        var goods = goodsService.listGoods(PageRequest.of(config.page(), config.size()));

        var totalPages = goods.getTotalPages();
        if (totalPages > 0 && config.page() + 1 > totalPages) {
            goods = goodsService.listGoods(PageRequest.of(totalPages - 1, config.size()));
        }

        return goods;
    }
}
