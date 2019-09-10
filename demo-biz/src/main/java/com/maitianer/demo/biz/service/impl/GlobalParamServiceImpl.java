package com.maitianer.demo.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maitianer.demo.biz.mapper.GlobalParamMapper;
import com.maitianer.demo.biz.service.GlobalParamService;
import com.maitianer.demo.model.sys.GlobalParam;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * User: Leo
 * Date: 2018/9/26 11:30 PM
 */
@Service
public class GlobalParamServiceImpl extends ServiceImpl<GlobalParamMapper, GlobalParam> implements GlobalParamService {

    @Override
    public Map<String, String> getAllParams(Integer paramType) {
        return null;
    }

    @Override
    public String getName(String paramKey) {
        return null;
    }

    @Override
    public String getValue(String paramKey, String defaultValue) {
        return null;
    }

    @Override
    public String getValue(String paramKey) {
        return getValue(paramKey, null);
    }
}
