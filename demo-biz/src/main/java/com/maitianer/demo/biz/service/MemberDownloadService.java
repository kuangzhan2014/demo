package com.maitianer.demo.biz.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.maitianer.demo.model.data.MemberDownloadBean;

/**
 * @Author Chen
 * @Date 2019/8/13 9:57
 */
public interface MemberDownloadService  extends IService<MemberDownloadBean> {

    /**
     * 用户下载素材
     * @param memberId
     * @param materialId
     */
    void memberDownload(Long memberId, Long materialId);

    /**
     * 下载量
     * @param materialId
     * @return
     */
    Integer DownloadCount(Long materialId, int year, int month, int day);

}
