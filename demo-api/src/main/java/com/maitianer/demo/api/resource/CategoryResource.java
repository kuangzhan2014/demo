package com.maitianer.demo.api.resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maitianer.demo.biz.service.CategoryService;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.model.data.CategoryBean;
import com.maitianer.demo.model.res.CategoryRES;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Chen
 * @Date 2019/8/30 15:43
 */
@Api(tags = "分类资源")
@RestController
@RequestMapping("category")
public class CategoryResource {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation("分类list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "brandId", dataType = "Long", required = true, paramType = "query", value = "品牌id"),
    })
    @GetMapping
    public List<CategoryRES> home(Long brandId) {
        List<CategoryBean> list = categoryService.list(new QueryWrapper<CategoryBean>().eq("brand_id", brandId)
                .eq("type", DomainConstants.CategoryType.PRODUCT)
                .eq("status", DomainConstants.CategoryStatus.NORMAL)
                .orderByAsc("sort").orderByDesc("create_date"));
        return list.stream().map(CategoryRES::new).collect(Collectors.toList());
    }
}
