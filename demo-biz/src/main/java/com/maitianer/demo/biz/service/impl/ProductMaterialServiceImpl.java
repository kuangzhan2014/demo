package com.maitianer.demo.biz.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maitianer.demo.biz.mapper.CategoryMaterialMapper;
import com.maitianer.demo.biz.mapper.ProductMaterialMapper;
import com.maitianer.demo.biz.service.CategoryMaterialService;
import com.maitianer.demo.biz.service.ProductMaterialService;
import com.maitianer.demo.model.data.CategoryMaterialBean;
import com.maitianer.demo.model.data.ProductMaterialBean;
import org.springframework.stereotype.Service;

/**
 * @Author Chen
 * @Date 2019/8/13 9:49
 */
@Service
public class ProductMaterialServiceImpl extends ServiceImpl<ProductMaterialMapper, ProductMaterialBean> implements ProductMaterialService{
}
