package com.maitianer.demo.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maitianer.demo.biz.service.CategoryProductService;
import com.maitianer.demo.biz.service.CategoryService;
import com.maitianer.demo.biz.service.MaterialService;
import com.maitianer.demo.biz.service.ProductService;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.model.common.DataTableRequest;
import com.maitianer.demo.model.common.DataTableResponse;
import com.maitianer.demo.model.common.ResultData;
import com.maitianer.demo.model.data.CategoryBean;
import com.maitianer.demo.model.data.CategoryProductBean;
import com.maitianer.demo.model.datatransfer.MaterialDTO;
import com.maitianer.demo.model.datatransfer.ProductDTO;
import com.maitianer.demo.model.query.CategoryQO;
import com.maitianer.demo.model.query.MaterialQO;
import com.maitianer.demo.model.query.ProductQO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author Chen
 * @Date 2019/8/14 9:43
 */
@Controller("CategoryController")
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private CategoryProductService categoryProductService;

    @RequiresPermissions("category:management")
    @GetMapping("list")
    public String list(Model model, CategoryQO categoryQO) {
        model.addAttribute("bean", categoryQO);
        return "category/list";
    }

    @ResponseBody
    @GetMapping("pageData")
    public DataTableResponse<CategoryBean> pageData(DataTableRequest<CategoryBean> pageRequest, CategoryQO categoryQO, HttpServletRequest httpServletRequest) {
        IPage<CategoryBean> page = categoryService.pageDate(pageRequest, categoryQO);
        return new DataTableResponse<CategoryBean>().success(page);
    }

    @RequiresPermissions("category:management")
    @GetMapping("form")
    public String form(Model model) {
        CategoryBean categoryBean = new CategoryBean();
        model.addAttribute("bean", categoryBean);
        return "category/form";
    }

    @RequiresPermissions("category:management")
    @GetMapping("{id}")
    public String edit(Model model, @PathVariable Long id) {
        CategoryBean categoryBean = categoryService.getById(id);
        model.addAttribute("bean", categoryBean);
        return "category/form";
    }

    /**
     * 创建分类
     *
     * @param categoryBean
     * @return
     */
    @RequiresPermissions("category:management")
    @PostMapping("saveOrUpdateCategory")
    @ResponseBody
    public ResultData saveOrUpdateCategory(@RequestBody CategoryBean categoryBean) {
        categoryBean.setStatus(DomainConstants.CategoryStatus.NORMAL);
        boolean result = categoryService.saveOrUpdate(categoryBean);
        return new ResultData(result ? 0 : 1);
    }

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    @RequiresPermissions("category:management")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultData delete(Long id) {
        boolean result = categoryService.update(new UpdateWrapper<CategoryBean>()
                .set("status", DomainConstants.CategoryStatus.DELETE).eq("id", id));
        categoryProductService.remove(new QueryWrapper<CategoryProductBean>().eq("category_id",id));
        return new ResultData<>(result ? 0 : 1, result);
    }

    @PostMapping(value = "getModuleCategoryManagement")
    @ResponseBody
    public List<CategoryBean> getModuleCategoryManagement(Long brandId) {
        List<CategoryBean> categoryList = categoryService.list(new QueryWrapper<CategoryBean>()
                .eq("brand_id", brandId)
                .eq("status", DomainConstants.CategoryStatus.NORMAL)
                .orderByAsc("sort").orderByDesc("create_date"));
        return categoryList;
    }

    @GetMapping("/products/{id}")
    public String detail(@PathVariable Long id, Model model) {
        ProductQO productQO = new ProductQO();
        productQO.setCategoryId(id);
        IPage<ProductDTO> productPage = productService.pageData(new Page<>(1, 6), productQO);
        MaterialQO materialQO = new MaterialQO();
        materialQO.setCategoryId(id);
        materialQO.setType(DomainConstants.MaterialType.PICTURE);
        IPage<MaterialDTO> picturePage = materialService.pageData(new Page<>(1, 6),materialQO);
        materialQO.setType(DomainConstants.MaterialType.VIDEO);
        IPage<MaterialDTO> videoPage = materialService.pageData(new Page<>(1, 6),materialQO);
        materialQO.setType(DomainConstants.MaterialType.TEXT);
        IPage<MaterialDTO> textPage = materialService.pageData(new Page<>(1, 6),materialQO);
        model.addAttribute("productList", productPage.getRecords());
        model.addAttribute("pictureList", picturePage.getRecords());
        model.addAttribute("videoList", videoPage.getRecords());
        model.addAttribute("textList", textPage.getRecords());
        model.addAttribute("id", id);
        return "category/detail";
    }
}
