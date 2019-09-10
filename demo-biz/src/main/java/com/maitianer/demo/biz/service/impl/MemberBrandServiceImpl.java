package com.maitianer.demo.biz.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maitianer.demo.biz.mapper.MemberBrandMapper;
import com.maitianer.demo.biz.service.MemberBrandService;
import com.maitianer.demo.model.data.MemberBrandBean;
import org.springframework.stereotype.Service;

/**
 * @Author Chen
 * @Date 2019/8/13 9:49
 */
@Service
public class MemberBrandServiceImpl extends ServiceImpl<MemberBrandMapper, MemberBrandBean> implements MemberBrandService {
}
