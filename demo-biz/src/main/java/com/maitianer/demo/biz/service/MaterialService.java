package com.maitianer.demo.biz.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maitianer.demo.model.data.MaterialBean;
import com.maitianer.demo.model.datatransfer.MaterialDTO;
import com.maitianer.demo.model.datatransfer.ProductDTO;
import com.maitianer.demo.model.query.MaterialQO;
import com.maitianer.demo.model.query.ProductQO;

/**
 * @Author Chen
 * @Date 2019/8/13 9:55
 */
public interface MaterialService extends IService<MaterialBean> {


    IPage<MaterialDTO> pageData(Page<MaterialDTO> pageRequest, MaterialQO materialQO);

    MaterialDTO saveData(MaterialDTO materialDTO);
}
