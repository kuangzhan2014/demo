package com.maitianer.demo.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maitianer.demo.admin.security.MemberSession;
import com.maitianer.demo.admin.util.MemberUtils;
import com.maitianer.demo.biz.service.*;
import com.maitianer.demo.biz.utils.MessageUtils;
import com.maitianer.demo.model.ApplicationData;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.model.common.DataTableRequest;
import com.maitianer.demo.model.common.DataTableResponse;
import com.maitianer.demo.model.common.ResultData;
import com.maitianer.demo.model.data.*;
import com.maitianer.demo.model.datatransfer.MaterialDTO;
import com.maitianer.demo.model.datatransfer.ProductDTO;
import com.maitianer.demo.model.query.BrandQO;
import com.maitianer.demo.model.query.MaterialQO;
import com.maitianer.demo.model.query.ProductQO;
import com.maitianer.demo.model.sys.GlobalParam;
import com.maitianer.demo.model.sys.Member;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author Chen
 * @Date 2019/8/13 15:20
 */
@Controller("BrandController")
@RequestMapping("brand")
public class BrandController extends BaseController {

    @Autowired
    private BrandService brandService;
    @Autowired
    private BrandEnterService brandEnterService;
    @Autowired
    private MemberBrandService memberBrandService;
    @Autowired
    private ProductService productService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private GlobalParamService globalParamService;


    @RequiresPermissions("brand:management")
    @GetMapping("list")
    public String list(Model model, BrandQO brandQO) {
        model.addAttribute("bean", brandQO);
        return "brand/list";
    }

    @ResponseBody
    @GetMapping("pageData")
    public DataTableResponse<BrandBean> pageData(DataTableRequest<BrandBean> pageRequest, BrandQO brandQO) {
        IPage<BrandBean> page = brandService.pageData(pageRequest, brandQO);
        return new DataTableResponse<BrandBean>().success(page);
    }

    @RequiresPermissions("brand:management")
    @GetMapping("add")
    public String add(BrandBean brandBean, Model model) {
        model.addAttribute("bean", brandBean);
        return "brand/form";
    }

    @RequiresPermissions("brand:management")
    @GetMapping("edit")
    public String edit(BrandBean brandBean, Model model) {
        BrandBean brand = brandService.getById(brandBean.getId());
        model.addAttribute("bean", brand);
        return "brand/form";
    }

    @RequiresPermissions("brand:management")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(BrandBean brandBean, RedirectAttributes redirectAttributes) {
        brandBean.setStatus(DomainConstants.BrandStatus.NORMAL);
        brandService.saveOrUpdate(brandBean);
        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:/brand/list";
    }

    @RequiresPermissions("brand:management")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultData delete(Long id) {
        boolean result = brandService.update(new UpdateWrapper<BrandBean>()
                .set("status", DomainConstants.CategoryStatus.DELETE).eq("id", id));
        memberBrandService.remove(new QueryWrapper<MemberBrandBean>().eq("brand_id",id));
        return new ResultData<>(result ? 0 : 1, result);
    }

    @GetMapping("/detail")
    public String detail(Model model) {
        Long brandId = MemberUtils.getMemberSession().getCurrentBrandId();
        ProductQO productQO = new ProductQO();
        productQO.setBrandId(brandId);
        IPage<ProductDTO> productPage = productService.pageData(new Page<>(1, 6), productQO);
        MaterialQO materialQO = new MaterialQO();
        materialQO.setBrandId(brandId);
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
        model.addAttribute("brandId",brandId);
        return "brand/detail";
    }


    @GetMapping("index")
    public String brandIndex(Model model) {
        Long memberId = MemberUtils.getMemberSession().getMemberId();
        List<BrandBean> brandList = brandService.list(new QueryWrapper<BrandBean>()
                .eq("status", DomainConstants.BrandStatus.NORMAL)
                .orderByAsc("sort").orderByDesc("create_date"));
        Map<Long, Long> memberBrandMap = memberBrandService.list(new QueryWrapper<MemberBrandBean>()
                .eq("member_id", memberId)).stream()
                .collect(Collectors.toMap(MemberBrandBean::getBrandId, MemberBrandBean::getId));
        List<BrandBean> inBrandList = new ArrayList<>();
        List<BrandBean> unInBrandList = new ArrayList<>();
        brandList.forEach(bean->{
            if(null != memberBrandMap.get(bean.getId())){
                inBrandList.add(bean);
            }else{
                unInBrandList.add(bean);
            }
        });
        GlobalParam brandBackground = globalParamService.getById(2);
        model.addAttribute("background", ApplicationData.get().getOssResourceUrl(brandBackground.getParamValue()));
        model.addAttribute("inBrandList",inBrandList);
        model.addAttribute("unInBrandList",unInBrandList);
        return "brand/index";
    }

    /**
     * 根据当前登录用户获取品牌列表
     *
     * @return
     */
    @ResponseBody
    @GetMapping(value = "fistMemberBrandByMember")
    public BrandBean fistMemberBrandByMember() {
        MemberSession membersession = MemberUtils.getMemberSession();
        if(null != membersession.getCurrentBrandId() && membersession.getCurrentBrandId() > 0 ){
            return brandService.getById(membersession.getCurrentBrandId());
        }else{
            List<BrandBean> list = brandService.listMemberBrandByMemberId(membersession.getMemberId());
            if (null != list && list.size() > 0) {
                membersession.setCurrentBrandId(list.get(0).getId());
                return list.get(0);
            }
        }
        return null;
    }

    /**
     * 获取所有品牌
     *
     * @param model
     * @return
     */
    @GetMapping(value = "selectBrand")
    public String selectPark(Model model) {
        Member member = MemberUtils.getCurrentMember();
        List<BrandBean> list = brandService.listMemberBrandByMemberId(member.getId());
        model.addAttribute("bean", list);
        return "brand/selectBrandForm";
    }


    @RequestMapping(value = "applyInBrand", method = RequestMethod.POST)
    @ResponseBody
    public ResultData applyInBrand(Long id) {
        Member member = MemberUtils.getCurrentMember();
        BrandBean brand = brandService.getById(id);
        String msg;
        BrandEnterBean brandEnterBean = brandEnterService.getOne(new QueryWrapper<BrandEnterBean>()
        .eq("brand_id",id).eq("initiator_id",member.getId())
        .eq("status",DomainConstants.BrandEnterStatus.UNDONE));
        if(null != brandEnterBean){
            msg = MessageUtils.get("brandEnter.fail");
        }else{
            brandEnterBean = new BrandEnterBean();
            brandEnterBean.setInitiatorId(member.getId());
            brandEnterBean.setInitiatorName(member.getRealName());
            brandEnterBean.setBrandId(id);
            brandEnterBean.setBrandName(brand.getName());
            brandEnterBean.setStatus(DomainConstants.BrandEnterStatus.UNDONE);
            brandEnterBean.setResult(DomainConstants.BrandEnterResult.NOTPASS);
            brandEnterService.save(brandEnterBean);
            msg = MessageUtils.get("brandEnter.success");
        }
        return new ResultData<>(msg);
    }

    @PostMapping(value = "brandList")
    @ResponseBody
    public List<BrandBean> brandList(Long brandId) {
        Long memberId = MemberUtils.getCurrentMember().getId();
        List<BrandBean> brandList = brandService.list(new QueryWrapper<BrandBean>()
                .ne("id", brandId)
                .eq("status", DomainConstants.BrandStatus.NORMAL)
                .orderByAsc("sort").orderByDesc("create_date"));
        if(memberService.isAdminAccount(memberId)){
            return brandList;
        }
        Map<Long, Long> memberBrandMap = memberBrandService.list(new QueryWrapper<MemberBrandBean>()
                .eq("member_id", memberId)).stream()
                .collect(Collectors.toMap(MemberBrandBean::getBrandId, MemberBrandBean::getId));
        List<BrandBean> inBrandList = new ArrayList<>();
        brandList.forEach(bean->{
            if(null != memberBrandMap.get(bean.getId())){
                inBrandList.add(bean);
            }
        });
        return inBrandList;
    }
}
