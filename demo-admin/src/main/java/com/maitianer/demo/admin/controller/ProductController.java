package com.maitianer.demo.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maitianer.demo.admin.util.MemberUtils;
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
import com.maitianer.demo.model.data.ProductBean;
import com.maitianer.demo.model.datatransfer.MaterialDTO;
import com.maitianer.demo.model.datatransfer.ProductDTO;
import com.maitianer.demo.model.query.MaterialQO;
import com.maitianer.demo.model.query.ProductQO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author Chen
 * @Date 2019/8/20 16:25
 */
@Controller("ProductController")
@RequestMapping("product")
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private CategoryProductService categoryProductService;


    @GetMapping("list")
    public String pictureList(ProductQO productQO, Model model) {
        model.addAttribute("bean", productQO);
        return "product/list";
    }

    @RequiresPermissions("product:management")
    @GetMapping("page")
    public String page(ProductQO productQO, Model model) {
//        IPage<MaterialBean> page = materialService.pageData(pageRequest, materialQO);
//        model.addAttribute("page",page);
        model.addAttribute("bean", productQO);
        return "product/page";
    }

    @ResponseBody
    @GetMapping("pageData")
    public DataTableResponse<ProductDTO> pageData(DataTableRequest<ProductDTO> pageRequest, ProductQO productQO) {
        IPage<ProductDTO> page = productService.pageData(pageRequest, productQO);
        return new DataTableResponse<ProductDTO>().success(page);
    }

    @ResponseBody
    @GetMapping("listData")
    public IPage<ProductDTO> listData(DataTableRequest<ProductDTO> pageRequest, ProductQO productQO) {
        pageRequest.setSize(6);
        IPage<ProductDTO> page = productService.pageData(pageRequest, productQO);
        return page;
    }

    @RequiresPermissions("product:management")
    @GetMapping("add")
    public String add(ProductDTO productDTO, Model model) {
        Long brandId = MemberUtils.getMemberSession().getCurrentBrandId();
        List<CategoryBean> list = categoryService.categoryList(brandId);
        productDTO.setBrandId(brandId);
        model.addAttribute("categoryList",list);
        model.addAttribute("bean", productDTO);
        return "product/form";
    }

    @RequiresPermissions("product:management")
    @GetMapping("edit")
    public String edit(ProductDTO productDTO, Model model) {
        ProductBean product = productService.getById(productDTO.getId());
        BeanUtils.copyProperties(product, productDTO);
        Long brandId = MemberUtils.getMemberSession().getCurrentBrandId();
        List<CategoryBean> list = categoryService.categoryList(brandId);
        Map<Long, Long> categoryProductMap = categoryProductService.list(new QueryWrapper<CategoryProductBean>()
                .eq("product_id", productDTO.getId())).stream().collect(Collectors.toMap(CategoryProductBean::getCategoryId, CategoryProductBean::getId));
        list.forEach(bean -> {
            if (null != categoryProductMap.get(bean.getId())) {
                bean.setStatus(DomainConstants.CategoryStatus.DELETE);
            }
        });
        model.addAttribute("categoryList",list);
        model.addAttribute("bean", productDTO);
        return "product/form";
    }

    @RequiresPermissions("product:management")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(ProductDTO productDTO, RedirectAttributes redirectAttributes) {
        productService.saveData(productDTO);
        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:/product/page";
    }

    @GetMapping("detail")
    public String detail(ProductDTO productDTO, Model model) {
        ProductBean product = productService.getById(productDTO.getId());
        MaterialQO materialQO = new MaterialQO();
        materialQO.setType(DomainConstants.MaterialType.PICTURE);
        materialQO.setProductId(product.getId());
        IPage<MaterialDTO> picturePage = materialService.pageData(new Page<>(1, 5),materialQO);
        materialQO.setType(DomainConstants.MaterialType.VIDEO);
        IPage<MaterialDTO> videoPage = materialService.pageData(new Page<>(1, 5),materialQO);
        materialQO.setType(DomainConstants.MaterialType.TEXT);
        IPage<MaterialDTO> textPage = materialService.pageData(new Page<>(1, 5),materialQO);
        model.addAttribute("pictureList", picturePage.getRecords());
        model.addAttribute("videoList", videoPage.getRecords());
        model.addAttribute("textList", textPage.getRecords());
        model.addAttribute("bean", product);
        return "product/detail";
    }

    @RequiresPermissions("product:management")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultData delete(Long id) {
        boolean result = productService.update(new UpdateWrapper<ProductBean>()
                .set("status", DomainConstants.CategoryStatus.DELETE).eq("id", id));
        return new ResultData<>(result ? 0 : 1, result);
    }
}
