package com.maitianer.demo.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maitianer.demo.admin.util.MemberUtils;
import com.maitianer.demo.biz.service.ProductSalesVolumeService;
import com.maitianer.demo.biz.service.ProductService;
import com.maitianer.demo.common.utils.EchartsUtils;
import com.maitianer.demo.common.utils.lang.DateUtils;
import com.maitianer.demo.common.utils.lang.StringUtils;
import com.maitianer.demo.model.common.DataTableRequest;
import com.maitianer.demo.model.data.ProductSalesVolumeBean;
import com.maitianer.demo.model.datatransfer.ProductDTO;
import com.maitianer.demo.model.datatransfer.ProductSalesDTO;
import com.maitianer.demo.model.query.ProductQO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author Chen
 * @Date 2019/9/4 15:06
 */
@Controller("ProductSalesController")
@RequestMapping("sales")
public class ProductSalesController {

    @Autowired
    private ProductSalesVolumeService productSalesVolumeServier;
    @Autowired
    private ProductService productService;

    @GetMapping("list")
    public String pictureList(ProductQO productQO, Model model) {
        model.addAttribute("bean", productQO);
        return "productSales/list";
    }

    @ResponseBody
    @GetMapping("listData")
    public IPage<ProductDTO> listData(DataTableRequest<ProductDTO> pageRequest, ProductQO productQO) {
        pageRequest.setSize(8);
        IPage<ProductDTO> page = productService.pageData(pageRequest, productQO);
        return page;
    }

    @GetMapping(value = "echarts")
    @ResponseBody
    public ProductSalesDTO echarts(ProductSalesDTO productSalesDTO) {
        Long memberId = MemberUtils.getCurrentMember().getId();
        // 默认当前日期
        if (StringUtils.isBlank(productSalesDTO.getDate())) {
            productSalesDTO.setDate(DateUtils.formatDate(new Date(), "yyyy-MM"));
        }
        Object[] dateArr = EchartsUtils.getDayArr(productSalesDTO.getDate()); // 获取查询月份的所有日期
        productSalesDTO.setDateArr(dateArr);
        String year = productSalesDTO.getDate().split("-")[0];
        String month = productSalesDTO.getDate().split("-")[1];
        List<ProductSalesVolumeBean> list = productSalesVolumeServier.echartsSales(memberId,productSalesDTO.getProductId(),Integer.valueOf(year),Integer.valueOf(month));
        Map<String, Integer> downloadMap = list.stream().collect(Collectors.toMap(ProductSalesVolumeBean::getDayLabel, ProductSalesVolumeBean::getSalesVolume));
        Object[] echarts = EchartsUtils.fillMissingData(dateArr, downloadMap);
        productSalesDTO.setProductSalesArr(echarts);
        return productSalesDTO;
    }

    @GetMapping("recordSales")
    public String recordSales(ProductSalesDTO productSalesDTO, Model model) {
        if (StringUtils.isBlank(productSalesDTO.getDate())) {
            productSalesDTO.setDate(DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
        }
        model.addAttribute("bean", productSalesDTO);
        return "productSales/recordSales";
    }

    @PostMapping("saveData")
    @ResponseBody
    public ProductSalesDTO saveData(ProductSalesDTO productSalesDTO) {
        Long memberId = MemberUtils.getCurrentMember().getId();
        String[] date = productSalesDTO.getDate().split("-");
        productSalesDTO.setYear(Integer.valueOf(date[0]));
        productSalesDTO.setMonth(Integer.valueOf(date[1]));
        productSalesDTO.setDay(Integer.valueOf(date[2]));
        productSalesDTO.setMemberId(memberId);
        productSalesVolumeServier.saveOrUpdateData(productSalesDTO);
        productSalesDTO.setDate(date[0]+"-"+date[1]);
        Object[] dateArr = EchartsUtils.getDayArr(productSalesDTO.getDate()); // 获取查询月份的所有日期
        productSalesDTO.setDateArr(dateArr);
        List<ProductSalesVolumeBean> list = productSalesVolumeServier.echartsSales(memberId,productSalesDTO.getProductId(),productSalesDTO.getYear(),productSalesDTO.getMonth());
        Map<String, Integer> downloadMap = list.stream().collect(Collectors.toMap(ProductSalesVolumeBean::getDayLabel, ProductSalesVolumeBean::getSalesVolume));
        Object[] echarts = EchartsUtils.fillMissingData(dateArr, downloadMap);
        productSalesDTO.setProductSalesArr(echarts);
        return productSalesDTO;
    }
}
