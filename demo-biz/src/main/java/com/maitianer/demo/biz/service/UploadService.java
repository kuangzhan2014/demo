package com.maitianer.demo.biz.service;

import com.maitianer.demo.common.model.ImageBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: zhou
 * @Date: 2019/08/21 15:29
 */

public interface UploadService {
    ImageBean uploadRichTextImage(HttpServletRequest request, HttpServletResponse response);

    /**
     * 上传照片
     * @param request
     * @param response
     * @return
     */
    List<ImageBean> uploadImage(HttpServletRequest request, HttpServletResponse response);
}
