package com.maitianer.demo.biz.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maitianer.demo.model.data.NoticeBean;
import com.maitianer.demo.model.datatransfer.NoticeDTO;
import com.maitianer.demo.model.query.NoticeQO;

/**
 * @Author Chen
 * @Date 2019/8/13 9:58
 */
public interface NoticeService extends IService<NoticeBean> {
    /**
     * 分页查询公告
     * @param pageRequest
     * @param noticeQO
     * @return
     */
    IPage<NoticeDTO> pageData(Page<NoticeDTO> pageRequest, NoticeQO noticeQO, Long brandId);

    /**
     * 保存公告
     * @param notice
     * @return
     */
    boolean saveData(NoticeDTO notice);

    /**
     * 查询公告
     * @param id
     * @return
     */
    NoticeDTO getData(Long id);

    boolean deleteData(Long id);
}
