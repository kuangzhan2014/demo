package com.maitianer.demo.admin.controller;

import com.maitianer.demo.common.web.DateEditor;
import com.maitianer.demo.common.web.Message;
import com.maitianer.demo.common.web.StringEditor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * User: Leo
 * Date: 2017/8/24 下午10:39
 */
public class BaseController {

    protected static final String ERROR_VIEW = "error";

    protected static final String FLASH_MESSAGE_ATTRIBUTE_NAME = "FLASH_MESSAGE";
    protected static final Message ERROR_MESSAGE = Message.error("操作失败！");
    protected static final Message SUCCESS_MESSAGE = Message.success("操作成功");

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Date.class, new DateEditor(true));
        binder.registerCustomEditor(String.class, "password", new StringEditor(true));
    }

    protected void addFlashMessage(RedirectAttributes redirectAttributes, Message message) {
        if (redirectAttributes != null && message != null) {
            redirectAttributes.addFlashAttribute(FLASH_MESSAGE_ATTRIBUTE_NAME, message);
        }
    }

    protected void addFlashMessage(RedirectAttributes redirectAttributes, String message) {
        if (redirectAttributes != null && message != null) {
            redirectAttributes.addFlashAttribute(FLASH_MESSAGE_ATTRIBUTE_NAME, message);
        }
    }

    protected static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    protected static void copyPropertiesIgnoreNull(Object src, Object target){
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

}
