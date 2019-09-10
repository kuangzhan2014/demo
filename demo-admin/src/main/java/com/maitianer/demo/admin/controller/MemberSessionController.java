package com.maitianer.demo.admin.controller;

import com.maitianer.demo.admin.security.MemberSession;
import com.maitianer.demo.admin.util.MemberUtils;
import com.maitianer.demo.biz.service.BrandService;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.model.common.ResultData;
import com.maitianer.demo.model.data.BrandBean;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

import static org.apache.shiro.web.filter.authc.FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME;
import static org.apache.shiro.web.filter.authc.FormAuthenticationFilter.DEFAULT_USERNAME_PARAM;

/**
 * User: Leo
 * Date: 2017/8/24 下午10:42
 */
@Controller("sysMemberSessionController")
@RequestMapping("sys")
public class MemberSessionController extends BaseController {

    @Autowired
    private BrandService brandService;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        if (SecurityUtils.getSubject() != null && SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect:/";
        }
        return "sys/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String loginFail(@RequestParam(DEFAULT_USERNAME_PARAM) String loginName,
                            HttpServletRequest request, RedirectAttributes redirect) {
        Object errorName = request
                .getAttribute(DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        if (errorName != null) {
            redirect.addFlashAttribute(DEFAULT_ERROR_KEY_ATTRIBUTE_NAME,
                    errorName);
        }
        redirect.addFlashAttribute("loginName", loginName);
        return "redirect:/sys/login";
    }

    @RequestMapping(value = "logout")
    public String logout() {
        return "redirect:/";
    }

    @PutMapping(value = "memberSession/updateCurrentBrandId")
    @ResponseBody
    public ResultData updateCurrentBrandId(@RequestParam Long brandId) {
        MemberSession session = MemberUtils.getMemberSession();
        BrandBean brand = brandService.getById(brandId);
        session.setCurrentBrandId(brandId);
        return new ResultData<>(DomainConstants.ResultDataCode.SUCCESS,brand);
    }

    @GetMapping(value = "forget")
    public String forgetPage() {
        return "sys/forget";
    }
}
