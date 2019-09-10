package com.maitianer.demo.api.resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maitianer.demo.biz.service.MaterialService;
import com.maitianer.demo.common.utils.lang.StringUtils;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.model.data.MaterialBean;
import com.maitianer.demo.model.res.MaterialDetailRES;
import com.maitianer.demo.model.res.MaterialRES;
import com.maitianer.demo.model.res.PageRES;
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
 * @Date 2019/8/30 15:24
 */
@Api(tags = "素材资源")
@RestController
@RequestMapping("materials")
public class MaterialResource {

    @Autowired
    private MaterialService materialService;

    @ApiOperation("素材列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", dataType = "integer", required = false, paramType = "query", value = "每页数量"),
            @ApiImplicitParam(name = "current", dataType = "integer", required = false, paramType = "query", value = "当前页码"),
            @ApiImplicitParam(name = "type", dataType = "integer", required = true, paramType = "query", value = "素材类型（1：图片，2：视频，3：话术）"),
            @ApiImplicitParam(name = "brandId", dataType = "Long", required = true, paramType = "query", value = "品牌id"),
            @ApiImplicitParam(name = "productId", dataType = "Long", required = false, paramType = "query", value = "产品id"),
            @ApiImplicitParam(name = "keywords", dataType = "String", required = false, paramType = "query", value = "搜索词")
    })
    @GetMapping
    public PageRES<MaterialRES> page(Page<MaterialBean> pageRequest, Integer type, Long brandId,Long productId,String keywords){
        QueryWrapper<MaterialBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("brand_id", brandId).eq("type", type)
                .eq("status", DomainConstants.MaterialStatus.NORMAL);
        if(null != productId && productId > 0){
            queryWrapper.exists("select 1 from t_product_material pm where pm.material_id = m.id and pm.product_id = " + productId);
        }
        if(StringUtils.isNotBlank(keywords)){
            queryWrapper.like("name",keywords);
        }
        queryWrapper.orderByAsc("sort").orderByDesc("create_date");
        IPage<MaterialBean> material = materialService.page(pageRequest,queryWrapper);
        return PageRES.success(material).map(MaterialRES::new);
    }

    @ApiOperation("素材详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "integer", required = true, paramType = "query", value = "素材id"),
    })
    @GetMapping("{id}")
    public MaterialDetailRES detail(@PathVariable Long id){
        MaterialBean material = materialService.getById(id);
        return new MaterialDetailRES(material);
    }
}
