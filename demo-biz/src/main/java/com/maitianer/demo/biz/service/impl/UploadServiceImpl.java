package com.maitianer.demo.biz.service.impl;

import com.maitianer.demo.biz.service.UploadService;
import com.maitianer.demo.common.model.ImageBean;
import com.maitianer.demo.common.utils.UploadUtils;
import com.maitianer.demo.core.filesystem.spring.FSProviderSpringFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhou
 * @Date: 2019/08/21 15:30
 */
@Service
public class UploadServiceImpl implements UploadService {
    @Autowired
    private FSProviderSpringFacade fsProvider;

    @Override
    public ImageBean uploadRichTextImage(HttpServletRequest request, HttpServletResponse response) {
        ImageBean imageBean = UploadUtils.uploadRichTextImageByInputStream(request, false);
        imageBean.setSrc(fsProvider.getDownloadUrl(imageBean.getTitle()));
        return imageBean;
    }

    @Override
    public List<ImageBean> uploadImage(HttpServletRequest request, HttpServletResponse response) {
        try{
            List<String> fileNames = UploadUtils.uploadImageByInputStream(request, false);
            List<ImageBean> imageBeanList = new ArrayList<>();
            if (fileNames.size() > 0) {
                fileNames.stream().forEach(bean -> {
                    ImageBean imageBean = new ImageBean();
                    imageBean.setTitle(bean);
                    imageBean.setSrc(fsProvider.getDownloadUrl(bean));
                    imageBeanList.add(imageBean);
                });
            }
            return imageBeanList;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
//            return new ResponseEntity<>( Message.warn("文件上传失败！"), HttpStatus.BAD_REQUEST);
        }
    }
}
