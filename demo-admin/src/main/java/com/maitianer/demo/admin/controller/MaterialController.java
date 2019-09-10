package com.maitianer.demo.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maitianer.demo.admin.util.MemberUtils;
import com.maitianer.demo.biz.service.*;
import com.maitianer.demo.common.utils.EchartsUtils;
import com.maitianer.demo.common.utils.HtmlToPdfUtils;
import com.maitianer.demo.common.utils.UploadUtils;
import com.maitianer.demo.common.utils.lang.DateUtils;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.model.common.DataTableRequest;
import com.maitianer.demo.model.common.DataTableResponse;
import com.maitianer.demo.model.common.ResultData;
import com.maitianer.demo.model.data.*;
import com.maitianer.demo.model.datatransfer.MaterialDTO;
import com.maitianer.demo.model.query.MaterialQO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author Chen
 * @Date 2019/8/16 15:18
 */
@Controller("MaterialController")
@RequestMapping("material")
public class MaterialController extends BaseController {

    @Autowired
    private MaterialService materialService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryMaterialService categoryMaterialService;
    @Autowired
    private ProductMaterialService productMaterialService;
    @Autowired
    private MemberDownloadService memberDownloadService;

    @GetMapping("list")
    public String pictureList(MaterialQO materialQO, Model model) {
//        IPage<MaterialBean> page = materialService.pageData(pageRequest, materialQO);
//        model.addAttribute("page",page);
        model.addAttribute("bean", materialQO);
        return "material/list";
    }

    @RequiresPermissions("material:management")
    @GetMapping("page")
    public String page(MaterialQO materialQO, Model model) {
//        IPage<MaterialBean> page = materialService.pageData(pageRequest, materialQO);
//        model.addAttribute("page",page);
        model.addAttribute("bean", materialQO);
        return "material/page";
    }

    @ResponseBody
    @GetMapping("pageData")
    public DataTableResponse<MaterialDTO> pageData(DataTableRequest<MaterialDTO> pageRequest, MaterialQO materialQO) {
        IPage<MaterialDTO> page = materialService.pageData(pageRequest, materialQO);
        return new DataTableResponse<MaterialDTO>().success(page);
    }

    @ResponseBody
    @GetMapping("listData")
    public IPage<MaterialDTO> listData(DataTableRequest<MaterialDTO> pageRequest, MaterialQO materialQO) {
        pageRequest.setSize(6);
        IPage<MaterialDTO> page = materialService.pageData(pageRequest, materialQO);
        return page;
    }

    @GetMapping("detail")
    public String detail(MaterialDTO materialDTO, Model model) {
        MaterialBean material = materialService.getById(materialDTO.getId());
        BeanUtils.copyProperties(material, materialDTO);
        model.addAttribute("bean", materialDTO);
        switch (materialDTO.getType()) {
            case DomainConstants.MaterialType.PICTURE:
                return "material/pictureDetail";
            case DomainConstants.MaterialType.VIDEO:
                return "material/videoDetail";
            case DomainConstants.MaterialType.TEXT:
                return "material/textDetail";
            default:
                return "/error";
        }
    }


    @ResponseBody
    @GetMapping("download/{id}")
    public void download(@PathVariable Long id, String resolution, HttpServletResponse response) {
        MaterialBean material = materialService.getById(id);
        if (DomainConstants.MaterialType.PICTURE == material.getType()) {
            UploadUtils.downloadFile(material.getShowPicture(), response);
        } else if (DomainConstants.MaterialType.TEXT == material.getType()) {
            //在下面，body中设置了style，设置了默认字体为宋体，这样导出时的html语言就默认带有了字体，汉字才会导出成功
            String content = "<html><body style=\"font-family: 黑体, SimHei;\">" +
                    "<p style=\"text-align: center;\"><span style=\"font-family: 黑体, SimHei; font-weight: bold; font-size: 24px;\">"
                    + material.getName() + "</span></p>" + material.getContent() + "</body></html>";
            HtmlToPdfUtils.htmltoPdf(content, response);
        } else if (DomainConstants.MaterialType.VIDEO == material.getType()) {
            String url = "outVideo/" + resolution + "/" + material.getVideoUrl() + "mp4";
            UploadUtils.downloadFile(url, response);
        }
        //记录下载量
        memberDownloadService.memberDownload(MemberUtils.getCurrentMember().getId(), material.getId());
    }

    @RequiresPermissions("material:management")
    @GetMapping("add")
    public String add(MaterialDTO materialDTO, Model model) {
        Long brandId = MemberUtils.getMemberSession().getCurrentBrandId();
        List<CategoryBean> categoryList = categoryService.categoryList(brandId);
        List<ProductBean> productList = productService.productList(brandId);
        materialDTO.setBrandId(brandId);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("productList", productList);
        model.addAttribute("bean", materialDTO);
        return "material/form";
    }

    @RequiresPermissions("material:management")
    @GetMapping("edit")
    public String edit(MaterialDTO materialDTO, Model model) {
        MaterialBean material = materialService.getById(materialDTO.getId());
        BeanUtils.copyProperties(material, materialDTO);
        Long brandId = MemberUtils.getMemberSession().getCurrentBrandId();
        List<CategoryBean> categoryList = categoryService.categoryList(brandId);
        List<ProductBean> productList = productService.productList(brandId);
        Map<Long, Long> categoryMaterialMap = categoryMaterialService.list(new QueryWrapper<CategoryMaterialBean>()
                .eq("material_id", materialDTO.getId())).stream()
                .collect(Collectors.toMap(CategoryMaterialBean::getCategoryId, CategoryMaterialBean::getId));
        Map<Long, Long> productMaterialMap = productMaterialService.list(new QueryWrapper<ProductMaterialBean>()
                .eq("material_id", materialDTO.getId())).stream()
                .collect(Collectors.toMap(ProductMaterialBean::getProductId, ProductMaterialBean::getId));
        categoryList.forEach(bean -> {
            if (null != categoryMaterialMap.get(bean.getId())) {
                bean.setStatus(DomainConstants.CategoryStatus.DELETE);
            }
        });
        productList.forEach(bean -> {
            if (null != productMaterialMap.get(bean.getId())) {
                bean.setStatus(DomainConstants.CategoryStatus.DELETE);
            }
        });
        MemberDownloadBean download = memberDownloadService.getOne(new QueryWrapper<MemberDownloadBean>()
                .select("SUM(count) count")
                .eq("material_id", materialDTO.getId()));
        materialDTO.setTotalDownloadCount(download.getCount());
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("productList", productList);
        model.addAttribute("bean", materialDTO);
        return "material/form";
    }

    @RequiresPermissions("material:management")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(MaterialDTO materialDTO, RedirectAttributes redirectAttributes) {
        materialDTO = materialService.saveData(materialDTO);
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DATE);
        MemberDownloadBean downloadBean = new MemberDownloadBean(MemberUtils.getCurrentMember().getId(), materialDTO.getId(), 0, year, month, day);
        memberDownloadService.save(downloadBean);
        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:/material/page";
    }

    @RequiresPermissions("material:management")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultData delete(Long id) {
        boolean result = materialService.update(new UpdateWrapper<MaterialBean>()
                .set("status", DomainConstants.CategoryStatus.DELETE).eq("id", id));
        return new ResultData<>(result ? 0 : 1, result);
    }

    @RequiresPermissions("material:management")
    @GetMapping("statistics")
    public String statistics(MaterialDTO materialDTO, Model model) {
        MaterialBean material = materialService.getById(materialDTO.getId());
        model.addAttribute("bean", material);
        return "material/echarts";
    }

    @GetMapping(value = "echarts")
    @ResponseBody
    public MaterialDTO echarts(MaterialDTO materialDTO) {
        // 默认当前日期
        if (materialDTO.getDate() == null) {
            materialDTO.setDate(DateUtils.formatDate(new Date(), "yyyy-MM"));
        }
        Object[] dateArr = EchartsUtils.getDayArr(materialDTO.getDate()); // 获取查询月份的所有日期
        materialDTO.setDateArr(dateArr);
        String year = materialDTO.getDate().split("-")[0];
        String month = materialDTO.getDate().split("-")[1];
        List<MemberDownloadBean> list = memberDownloadService.list(new QueryWrapper<MemberDownloadBean>()
                .select("SUM(count) as count, day")
                .eq("material_id", materialDTO.getId()).eq("year", year)
                .eq("month", month).groupBy("day"));
        Map<String, Integer> downloadMap = list.stream().collect(Collectors.toMap(MemberDownloadBean::getDayLabel, MemberDownloadBean::getCount));
        Object[] echarts = EchartsUtils.fillMissingData(dateArr, downloadMap);
        materialDTO.setDownloadCountArr(echarts);
        return materialDTO;
    }

}
