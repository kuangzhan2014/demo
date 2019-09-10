package com.maitianer.demo.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maitianer.demo.biz.service.GlobalParamService;
import com.maitianer.demo.common.utils.lang.StringUtils;
import com.maitianer.demo.model.sys.GlobalParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * User: Leo
 * Date: 2018/9/27 8:47 PM
 */
@Controller
@RequestMapping("sys/param")
public class GlobalParamController extends BaseController {

    @Autowired
    private GlobalParamService globalParamService;

    @RequiresPermissions("system:param")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Page<GlobalParam> pageRequest, Integer paramGroup, String searchProperty, String searchValue, Model model) {
        QueryWrapper<GlobalParam> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(searchProperty) && StringUtils.isNotBlank(searchValue)) {
            wrapper.like(searchProperty, searchValue);
        }

        if (paramGroup != null) {
            wrapper.eq("param_group", paramGroup);
        }

        IPage<GlobalParam> page = globalParamService.page(pageRequest, wrapper);
        model.addAttribute("page", page);
        model.addAttribute("paramGroup", paramGroup);
        model.addAttribute("searchProperty", searchProperty);
        model.addAttribute("searchValue", searchValue);
        return "sys/globalParam/list";
    }

    @RequiresPermissions("system:param")
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(@RequestParam Long id, Model model) {
        GlobalParam param = globalParamService.getById(id);
        model.addAttribute("bean", param);
        return "sys/globalParam/form";
    }

    @RequiresPermissions("system:param")
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("bean", new GlobalParam());
        return "sys/globalParam/form";
    }

    @RequiresPermissions("system:param")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(GlobalParam bean, RedirectAttributes redirectAttributes) {
        globalParamService.saveOrUpdate(bean);
        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:list";
    }

    @RequiresPermissions("system:param")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String delete(Long[] ids) {
        return "redirect:list";
    }
}
