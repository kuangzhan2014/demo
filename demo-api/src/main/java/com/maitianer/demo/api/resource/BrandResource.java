package com.maitianer.demo.api.resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maitianer.demo.api.security.CurrentUser;
import com.maitianer.demo.biz.service.BrandService;
import com.maitianer.demo.biz.service.MemberBrandService;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.model.data.BrandBean;
import com.maitianer.demo.model.data.MemberBrandBean;
import com.maitianer.demo.model.res.BrandRES;
import com.maitianer.demo.model.sys.Member;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author Chen
 * @Date 2019/8/30 12:05
 */
@Api(tags = "品牌资源")
@RestController
@RequestMapping("brands")
public class BrandResource {
    @Autowired
    private BrandService brandService;
    @Autowired
    private MemberBrandService memberBrandService;

    @ApiOperation("用户品牌")
    @GetMapping
    public List<BrandRES> list(@CurrentUser Member member){
        List<BrandBean> brandList = brandService.list(new QueryWrapper<BrandBean>()
                .eq("status", DomainConstants.BrandStatus.NORMAL)
                .orderByAsc("sort").orderByDesc("create_date"));
        Map<Long, Long> memberBrandMap = memberBrandService.list(new QueryWrapper<MemberBrandBean>()
                .eq("member_id", member.getId())).stream()
                .collect(Collectors.toMap(MemberBrandBean::getBrandId, MemberBrandBean::getId));
        brandList.forEach(bean->{
            if(null == memberBrandMap.get(bean.getId())){
                bean.setStatus(DomainConstants.BrandStatus.DELETE);
            }
        });
        return brandList.stream().map(BrandRES::new).collect(Collectors.toList());
    }

}
