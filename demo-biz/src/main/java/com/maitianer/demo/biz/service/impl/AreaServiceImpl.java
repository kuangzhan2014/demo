package com.maitianer.demo.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maitianer.demo.biz.mapper.AreaMapper;
import com.maitianer.demo.biz.service.AreaService;
import com.maitianer.demo.biz.service.DictService;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.model.sys.Area;
import com.maitianer.demo.model.sys.Member;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Mr.Zhang
 * @Date: 2018-09-28 15:05
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService {
    @Override
    public List<Area> selectRootArea() {
        QueryWrapper wrapper = new QueryWrapper<Area>().eq("parent_code",DomainConstants.AREA_CODE_CN).and(w->w.ne("area_code",DomainConstants.AREA_CODE_DYD));
        wrapper.orderByAsc("id");
        return baseMapper.selectArea(wrapper);
    }

    @Override
    public List<Area> selectSubArea(String code) {
//        if(code != null) {
            QueryWrapper wrapper = new QueryWrapper<Area>().eq("parent_code", code);
            wrapper.orderByAsc("id");
            return baseMapper.selectArea(wrapper);
//        }else {return new ArrayList<>();}
    }

    @Override
    public Member selectCompletelArea(Member member) {
        String districtCode =member.getAreaCode();
        QueryWrapper wrapper = new QueryWrapper<Area>().eq("area_code", districtCode);
        Area area = baseMapper.selectOne(wrapper);
        member.setDistrict(area.getAreaName());

        QueryWrapper wrapper1 = new QueryWrapper<Area>().eq("area_code", area.getParentCode());
        Area area1 = baseMapper.selectOne(wrapper1);
        member.setCityCode(area1.getAreaCode());
        member.setCity(area1.getAreaName());

        QueryWrapper wrapper2 = new QueryWrapper<Area>().eq("area_code", area1.getParentCode());
        Area area2 = baseMapper.selectOne(wrapper2);
        member.setProvinceCode(area2.getAreaCode());
        member.setProvince(area2.getAreaName());

//        if(member.getDistrictCode()!=null){
//           member.setDistrictCode(baseMapper.selectOne(new QueryWrapper<Area>().eq("area_name",member.getDistrict())).getAreaCode());}
//        if(member.getCityCode()!=null) {
//            member.setCity(baseMapper.selectOne(new QueryWrapper<Area>().eq("area_name", member.getCity())).getAreaCode());
//        }
//        if(member.getProvinceCode()!=null) {
//            member.setProvinceCode(baseMapper.selectOne(new QueryWrapper<Area>().eq("area_name", member.getProvince())).getAreaCode());
//        }
         return member;
    }

    @Override
    public Member codeToAreaName(Member member) {
        member.setDistrict(getOne(new QueryWrapper<Area>().eq("area_code",member.getAreaCode())).getAreaName());
        member.setCity(getOne(new QueryWrapper<Area>().eq("area_code",member.getCityCode())).getAreaName());
        member.setProvince(getOne(new QueryWrapper<Area>().eq("area_code",member.getProvinceCode())).getAreaName());
        return member;
    }
}
