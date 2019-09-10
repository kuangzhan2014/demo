package com.maitianer.demo.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maitianer.demo.biz.service.DictService;
import com.maitianer.demo.common.utils.lang.StringUtils;
import com.maitianer.demo.model.sys.Dict;
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
 * Date: 2018/1/28 下午5:00
 */
@Controller("sysDictController")
@RequestMapping("sys/dict")
public class DictController extends BaseController {

    @Autowired
    private DictService dictService;

    @RequiresPermissions("system:dict")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Page<Dict> pageRequest, String searchProperty, String searchValue, Model model) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(searchProperty) && StringUtils.isNotBlank(searchValue)) {
            wrapper.like(searchProperty, searchValue);
        }
        IPage<Dict> page = dictService.page(pageRequest, wrapper);
        model.addAttribute("page", page);
        model.addAttribute("searchProperty", searchProperty);
        model.addAttribute("searchValue", searchValue);
        return "sys/dict/list";
    }

    @RequiresPermissions("system:dict")
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(@RequestParam Long id, Model model) {
        Dict dict = dictService.getById(id);
        model.addAttribute("bean", dict);
        return "sys/dict/form";
    }

    @RequiresPermissions("system:dict")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(Dict dict, RedirectAttributes redirectAttributes) {
        dictService.updateById(dict);
        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:list";
    }
}
