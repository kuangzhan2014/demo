package com.maitianer.demo.biz.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maitianer.demo.biz.mapper.MemberDownloadMapper;
import com.maitianer.demo.biz.service.MemberDownloadService;
import com.maitianer.demo.model.data.MemberDownloadBean;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

/**
 * @Author Chen
 * @Date 2019/8/13 9:49
 */
@Service
public class MemberDownloadServiceImpl extends ServiceImpl<MemberDownloadMapper, MemberDownloadBean> implements MemberDownloadService {

    @Override
    public void memberDownload(Long memberId, Long materialId) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DATE);
        MemberDownloadBean memberDownload = getOne(new QueryWrapper<MemberDownloadBean>()
                .eq("member_id", memberId).eq("material_id", materialId)
                .eq("year", year).eq("month", month).eq("day", day));
        if (memberDownload == null) {
            MemberDownloadBean downloadBean = new MemberDownloadBean(memberId,materialId,1,year,month,day);
            save(downloadBean);
        }else{
            memberDownload.setCount(memberDownload.getCount()+1);
            updateById(memberDownload);
        }
    }

    @Override
    public Integer DownloadCount(Long materialId, int year, int month, int day) {
        QueryWrapper<MemberDownloadBean> queryWrapper = new QueryWrapper<>();
        if(year != 0){
            queryWrapper.eq("year",year);
            if(month != 0){
                queryWrapper.eq("month",month);
                if(day!=0){
                    queryWrapper.eq("day",day);
                }
            }
        }
        queryWrapper.eq("material_id",materialId);
        queryWrapper.select("SUM(count) as count");
        MemberDownloadBean downloadBean = Optional.ofNullable(getOne(queryWrapper)).orElse(new MemberDownloadBean());
        return downloadBean.getCount();
    }
}
