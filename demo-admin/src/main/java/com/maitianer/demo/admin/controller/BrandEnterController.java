package com.maitianer.demo.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maitianer.demo.admin.util.MemberUtils;
import com.maitianer.demo.biz.service.BrandEnterService;
import com.maitianer.demo.biz.service.MemberBrandService;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.model.common.DataTableRequest;
import com.maitianer.demo.model.common.DataTableResponse;
import com.maitianer.demo.model.common.ResultData;
import com.maitianer.demo.model.data.BrandEnterBean;
import com.maitianer.demo.model.data.MemberBrandBean;
import com.maitianer.demo.model.query.BrandEnterQO;
import com.maitianer.demo.model.sys.Member;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @Author Chen
 * @Date 2019/8/27 11:34
 */
@Controller("BrandEnterController")
@RequestMapping("brandEnter")
public class BrandEnterController {

    @Autowired
    private BrandEnterService brandEnterService;
    @Autowired
    private MemberBrandService memberBrandService;

    @RequiresPermissions("brandEnter:management")
    @GetMapping("list")
    public String list(Model model, BrandEnterQO brandEnterQO) {
        model.addAttribute("bean", brandEnterQO);
        return "brandEnter/list";
    }

    @ResponseBody
    @GetMapping("pageData")
    public DataTableResponse<BrandEnterBean> pageData(DataTableRequest<BrandEnterBean> pageRequest, BrandEnterQO brandEnterQO) {
        IPage<BrandEnterBean> page = brandEnterService.pageData(pageRequest, brandEnterQO);
        return new DataTableResponse<BrandEnterBean>().success(page);
    }

    @RequiresPermissions("brandEnter:management")
    @RequestMapping(value = "isPass", method = RequestMethod.POST)
    @ResponseBody
    public ResultData isPass(Long id, boolean isPass) {
        BrandEnterBean brandEnter = brandEnterService.getById(id);
        brandEnter.setStatus(DomainConstants.BrandEnterStatus.DONE);
        if(isPass){
            brandEnter.setResult(DomainConstants.BrandEnterResult.PASS);
            MemberBrandBean memberBrandBean = new MemberBrandBean();
            memberBrandBean.setBrandId(brandEnter.getBrandId());
            memberBrandBean.setMemberId(brandEnter.getInitiatorId());
            memberBrandService.save(memberBrandBean);
        }else{
            brandEnter.setResult(DomainConstants.BrandEnterResult.NOTPASS);
            MemberBrandBean memberBrand = memberBrandService.getOne(new QueryWrapper<MemberBrandBean>().eq("brand_id", brandEnter.getBrandId())
                    .eq("member_id", brandEnter.getInitiatorId()));
            if(null != memberBrand){
                memberBrandService.removeById(memberBrand.getId());
            }
        }
        Member memberBean = MemberUtils.getCurrentMember();
        brandEnter.setHandlerId(memberBean.getId());
        brandEnter.setHandlerName(memberBean.getRealName());
        brandEnter.setHandleDate(new Date());
        boolean result = brandEnterService.updateById(brandEnter);
        return new ResultData<>(result ? 0 : 1, result);
    }

}
