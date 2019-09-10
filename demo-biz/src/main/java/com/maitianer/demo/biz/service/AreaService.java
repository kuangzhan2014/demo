package com.maitianer.demo.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maitianer.demo.common.web.Message;
import com.maitianer.demo.model.sys.Area;
import com.maitianer.demo.model.sys.Member;

import java.util.List;

/**
 * User: Leo
 * Date: 2017/8/26 下午1:57
 */
public interface AreaService extends IService<Area> {
    /**
     * 功能描述: 查询根地区(省，直辖市，自治区，特别行政区)
     * @Param: []
     * @Return: java.util.List<com.maitianer.demo.model.sys.Area>
     */
    List<Area> selectRootArea();

    /**
     * 功能描述: 查找下一级地区
     * @Param: [code]
     * @Return: java.util.List<com.maitianer.demo.model.sys.Area>
     */
    List<Area> selectSubArea(String code);

    /**
     * 功能描述: 根据区编码查找对应的省份名、省份编码，城市名、城市编码，区名,
     * @Param: [member]
     * @Return: com.maitianer.demo.model.sys.Member
     */
    Member selectCompletelArea(Member member);

    /**
     * 功能描述: 将编码转换为对应的地区名
     * @Param: [member]
     * @Return: com.maitianer.demo.model.sys.Member
     */
    Member codeToAreaName(Member member);

}
