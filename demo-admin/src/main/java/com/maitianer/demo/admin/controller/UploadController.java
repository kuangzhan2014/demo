package com.maitianer.demo.admin.controller;

import com.maitianer.demo.biz.service.UploadService;
import com.maitianer.demo.common.errors.BadRequestAlertException;
import com.maitianer.demo.common.model.ImageBean;
import com.maitianer.demo.common.spring.SpringContextHolder;
import com.maitianer.demo.common.utils.UploadUtils;
import com.maitianer.demo.common.web.Message;
import com.maitianer.demo.core.filesystem.spring.FSProviderSpringFacade;
import com.maitianer.demo.model.common.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Leo
 * Date: 2018/2/15 下午9:17
 */
@RestController("commonUploadController")
@RequestMapping(value = "/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @RequestMapping("imageByInputStream")
    public Object uploadImageByInputStream(HttpServletRequest request, HttpServletResponse response) {
        List<String> fileNames = UploadUtils.uploadImageByInputStream(request, false);
        if (fileNames.size() > 0) {
            return fileNames;
        } else {
            return new ResponseEntity<>(Message.warn("请选择要上传的文件！"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("createHtmlUploadToken")
    public ResponseEntity<?> createHtmlUploadToken() {
        FSProviderSpringFacade fsProviderSpringFacade = SpringContextHolder.getBean("aliyunFSProvider");
        if (fsProviderSpringFacade == null) {
            throw new RuntimeException("未找到aliyunFSProvider！");
        }
        Map<String, String> map = fsProviderSpringFacade.createHtmlUploadToken(600L);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping("download")
    public void download(String url, HttpServletResponse response) {
        if (url.length() > 0) {
//            UploadUtils.downloadFile(url, response);
        } else {
            throw new BadRequestAlertException("请选择要上传的文件！");
        }

    }
    @RequestMapping("file")
    public ResponseEntity<?> uploadFile(HttpServletRequest request, HttpServletResponse response) {
        List<String> fileNames = UploadUtils.uploadFile(request);

        if (fileNames.size() > 0) {
            return new ResponseEntity<>(fileNames, HttpStatus.OK);
        }else {
            return new ResponseEntity<>( Message.warn("请选择要上传的文件！"), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("image")
    public Object uploadImage(HttpServletRequest request, HttpServletResponse response) {
        try{
            List<String> fileNames = UploadUtils.uploadImageByInputStream(request, false);
            if (fileNames.size() > 0) {
                return fileNames;
            }else {
                return new ResponseEntity<>( Message.warn("请选择要上传的文件！"), HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>( Message.warn("文件上传失败！"), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 上传图片
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("uploadImage")
    public ResultData<List<ImageBean>> uploadImage2(HttpServletRequest request, HttpServletResponse response) {
        List<ImageBean> imageBeanList = uploadService.uploadImage(request, response);
        if(imageBeanList.size() <= 0) {
            return new ResultData<>(1,"请选择要上传的文件!");
        }
        return new ResultData<>(imageBeanList);
    }

    /**
     * 上传图片
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("tinyMCEUploadImage")
    @ResponseBody
    public Map<String, Object> tinyMCEUploadImage(HttpServletRequest request, HttpServletResponse response) {
        List<ImageBean> imageBeanList = uploadService.uploadImage(request, response);
        Map<String, Object> result = new HashMap<>();
        if(imageBeanList.size() <= 0) {
            result.put("location", "");
        } else {
            result.put("location", imageBeanList.get(0).getSrc());
        }
        return result;
    }

    /**
     * 上传富文本图片（Layui格式）
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("richTextImage")
    public ResultData<ImageBean> uploadRichTextImage(HttpServletRequest request, HttpServletResponse response) {
        ResultData<ImageBean> resultData = new ResultData<>(uploadService.uploadRichTextImage(request, response));
        return resultData;
    }

}
