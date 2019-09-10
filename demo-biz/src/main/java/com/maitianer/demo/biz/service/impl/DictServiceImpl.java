package com.maitianer.demo.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maitianer.demo.biz.mapper.DictMapper;
import com.maitianer.demo.biz.service.DictService;
import com.maitianer.demo.model.sys.Dict;
import org.springframework.stereotype.Service;

/**
 * User: Leo
 * Date: 2018/1/27 下午4:57
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
}
