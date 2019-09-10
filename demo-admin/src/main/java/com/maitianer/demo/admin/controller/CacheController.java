package com.maitianer.demo.admin.controller;

import com.maitianer.demo.biz.utils.DictUtils;
import com.maitianer.demo.biz.utils.GlobalParamUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * User: Leo
 * Date: 2018/1/28 下午4:59
 */
@Controller("sysCacheController")
@RequestMapping("sys/cache")
public class CacheController extends BaseController {

    @RequiresPermissions("system:cache")
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {
        return "sys/cache/index";
    }

    @RequiresPermissions("system:cache")
    @RequestMapping(value = "resetAuthorization", method = RequestMethod.GET)
    public String resetAuthorization(RedirectAttributes redirectAttributes) {
//        if (memberRealm.getAuthorizationCache() != null) {
//            memberRealm.getAuthorizationCache().clear();
//        }
        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:index";
    }

    @RequiresPermissions("system:cache")
    @RequestMapping(value = "resetDict", method = RequestMethod.GET)
    public String resetDict(RedirectAttributes redirectAttributes) {
        DictUtils.loadDictData();
        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:index";
    }

    @RequiresPermissions("system:cache")
    @RequestMapping(value = "resetGlobalParam", method = RequestMethod.GET)
    public String resetGlobalParam(RedirectAttributes redirectAttributes) {
        GlobalParamUtils.loadGlobalParams();
        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:index";
    }

}
