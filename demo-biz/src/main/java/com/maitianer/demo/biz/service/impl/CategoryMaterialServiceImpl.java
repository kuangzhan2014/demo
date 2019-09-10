package com.maitianer.demo.biz.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maitianer.demo.biz.mapper.CategoryMaterialMapper;
import com.maitianer.demo.biz.mapper.CategoryProductMapper;
import com.maitianer.demo.biz.service.CategoryMaterialService;
import com.maitianer.demo.biz.service.CategoryProductService;
import com.maitianer.demo.model.data.CategoryMaterialBean;
import com.maitianer.demo.model.data.CategoryProductBean;
import org.springframework.stereotype.Service;

/**
 * @Author Chen
 * @Date 2019/8/13 9:49
 */
@Service
public class CategoryMaterialServiceImpl extends ServiceImpl<CategoryMaterialMapper, CategoryMaterialBean> implements CategoryMaterialService{
}
