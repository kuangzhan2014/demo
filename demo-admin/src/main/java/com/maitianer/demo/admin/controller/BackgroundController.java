package com.maitianer.demo.admin.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.maitianer.demo.biz.service.GlobalParamService;
import com.maitianer.demo.common.utils.UploadUtils;
import com.maitianer.demo.common.web.Message;
import com.maitianer.demo.model.ApplicationData;
import com.maitianer.demo.model.sys.GlobalParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author Chen
 * @Date 2019/9/5 16:48
 */
@Controller("BackgroundController")
@RequestMapping("back")
public class BackgroundController {

    @Autowired
    private GlobalParamService globalParamService;

    @GetMapping("detail")
    public String detail(Model model){
        GlobalParam login = globalParamService.getById(1);
        login.setParamValue(ApplicationData.get().getOssResourceUrl(login.getParamValue()));
        GlobalParam brand = globalParamService.getById(2);
        brand.setParamValue(ApplicationData.get().getOssResourceUrl(brand.getParamValue()));
        model.addAttribute("login", login);
        model.addAttribute("brand",brand);
        return "back/detail";
    }

    @RequestMapping("uploadLoginImage")
    @ResponseBody
    public Object uploadLoginImage(HttpServletRequest request, HttpServletResponse response) {
        List<String> fileNames = UploadUtils.uploadImageByInputStream(request, false);
        if (fileNames.size() > 0) {
            globalParamService.update(new UpdateWrapper<GlobalParam>()
                    .set("param_value",fileNames.get(0))
                    .eq("id",1));
            return fileNames;
        } else {
            return new ResponseEntity<>(Message.warn("请选择要上传的文件！"), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("uploadBrandImage")
    @ResponseBody
    public Object uploadBrandImage(HttpServletRequest request, HttpServletResponse response) {
        List<String> fileNames = UploadUtils.uploadImageByInputStream(request, false);
        if (fileNames.size() > 0) {
            globalParamService.update(new UpdateWrapper<GlobalParam>()
                    .set("param_value",fileNames.get(0))
                    .eq("id",2));
            return fileNames;
        } else {
            return new ResponseEntity<>(Message.warn("请选择要上传的文件！"), HttpStatus.BAD_REQUEST);
        }
    }
}
