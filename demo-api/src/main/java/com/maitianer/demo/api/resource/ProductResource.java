package com.maitianer.demo.api.resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maitianer.demo.biz.service.ProductService;
import com.maitianer.demo.common.utils.lang.StringUtils;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.model.data.ProductBean;
import com.maitianer.demo.model.res.PageRES;
import com.maitianer.demo.model.res.ProductRES;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Chen
 * @Date 2019/8/30 15:32
 */
@Api(tags = "产品资源")
@RestController
@RequestMapping("products")
public class ProductResource {

    @Autowired
    private ProductService productService;


    @ApiOperation("产品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", dataType = "integer", required = false, paramType = "query", value = "每页数量"),
            @ApiImplicitParam(name = "current", dataType = "integer", required = false, paramType = "query", value = "当前页码"),
            @ApiImplicitParam(name = "brandId", dataType = "Long", required = true, paramType = "query", value = "品牌id"),
            @ApiImplicitParam(name = "categoryId", dataType = "Long", required = true, paramType = "query", value = "分类id"),
            @ApiImplicitParam(name = "keywords", dataType = "String", required = false, paramType = "query", value = "搜索词")
    })
    @GetMapping
    public PageRES<ProductRES> page(Page<ProductBean> pageRequest, Long categoryId, Long brandId,String keywords){
        QueryWrapper<ProductBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("brand_id", brandId);
        queryWrapper.exists("select 1 from t_category_product cp where cp.product_id = p.id and cp.category_id = " + categoryId);
        if(StringUtils.isNotBlank(keywords)){
            queryWrapper.like("name",keywords);
        }
        queryWrapper.eq("status", DomainConstants.MaterialStatus.NORMAL)
                .orderByAsc("sort").orderByDesc("create_date");
        IPage<ProductBean> productPage = productService.page(pageRequest,queryWrapper);
        return PageRES.success(productPage).map(ProductRES::new);
    }

    @ApiOperation("产品详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "Long", required = false, paramType = "query", value = "产品id")
    })
    @GetMapping("{id}")
    public ProductRES detail(@PathVariable Long id){
        ProductBean product = productService.getById(id);
        return new ProductRES(product);
    }
}
