package com.maitianer.demo.biz.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maitianer.demo.biz.mapper.NoticeMapper;
import com.maitianer.demo.biz.service.NoticeService;
import com.maitianer.demo.common.utils.lang.StringUtils;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.model.data.NoticeBean;
import com.maitianer.demo.model.datatransfer.NoticeDTO;
import com.maitianer.demo.model.query.NoticeQO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @Author Chen
 * @Date 2019/8/13 9:49
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, NoticeBean> implements NoticeService {
    @Override
    public IPage<NoticeDTO> pageData(Page<NoticeDTO> pageRequest, NoticeQO noticeQO, Long brandId) {
        QueryWrapper<NoticeDTO> queryWrapper = new QueryWrapper();
        if (noticeQO != null) {
            // 标题
            if (StringUtils.isNotBlank(noticeQO.getTitle())) {
                queryWrapper.like("title", noticeQO.getTitle());
            }
        }
        if (brandId != null) {
            //选择当前品牌
            queryWrapper.eq("brand_id", brandId);
        }
        // 过滤已删除的公告
        queryWrapper.eq("status", DomainConstants.NoticeStatus.NORMAL);
        IPage<NoticeDTO> page = baseMapper.pageData(pageRequest, queryWrapper);
        return page;

    }

    /**
     * 保存公告
     *
     * @param notice
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveData(NoticeDTO notice) {

        if (notice.getId() == null) {
            notice.setStatus(DomainConstants.NoticeStatus.NORMAL);
            save(notice);
        } else {
            updateById(notice);
        }

        return true;
    }

    @Override
    public NoticeDTO getData(Long id) {
        NoticeBean notice = getOne(new QueryWrapper<NoticeBean>().eq("id", id));
        NoticeDTO noticeDTO = new NoticeDTO();
        BeanUtils.copyProperties(notice, noticeDTO);

        return noticeDTO;
    }


    @Override
    public boolean deleteData(Long id) {
        NoticeBean notice = getOne(new QueryWrapper<NoticeBean>().eq("id", id));
        notice.setStatus(DomainConstants.NoticeStatus.DELETED);
        boolean result = updateById(notice);
        return result;
    }
}
