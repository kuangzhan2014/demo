package com.maitianer.demo.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maitianer.demo.biz.service.PermissionService;
import com.maitianer.demo.common.utils.lang.StringUtils;
import com.maitianer.demo.common.web.Message;
import com.maitianer.demo.model.sys.Permission;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * User: Leo
 * Date: 2018/1/28 下午4:26
 */
@Controller("sysPermissionController")
@RequestMapping("sys/permission")
public class PermissionController extends BaseController {

    @Autowired
    private PermissionService permissionService;

    @ModelAttribute("rootPermissions")
    public List<Permission> rootPermissions() {
        return permissionService.findRootPermissions();
    }

    @RequiresPermissions("system:permission")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(String searchProperty, String searchValue, Model model) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(searchProperty) && StringUtils.isNotBlank(searchValue)) {
            wrapper.like(searchProperty, searchValue);
        }
        List<Permission> permissions = permissionService.findAll(wrapper);
        model.addAttribute("permissions", permissions);
        model.addAttribute("searchProperty", searchProperty);
        model.addAttribute("searchValue", searchValue);
        return "sys/permission/list";
    }

    @RequiresPermissions("system:permission")
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Long parentId, Model model) {
        Permission parent = permissionService.getById(parentId);
        Permission permission = new Permission();
        if (parent != null) {
            permission.setParentId(parentId);
            if (parent.getPermissionValue().contains(":")) {
                permission.setPermissionValue(parent.getPermissionValue().split(":")[0] + ":");
            }else {
                permission.setPermissionValue(parent.getPermissionValue() + ":");
            }
        }
        model.addAttribute("bean", permission);
        return "sys/permission/form";
    }

    @RequiresPermissions("system:permission")
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Long id, Model model) {
        model.addAttribute("bean", permissionService.getById(id));
        return "sys/permission/form";
    }

    @RequiresPermissions("system:permission")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(Permission permission, RedirectAttributes redirectAttributes) {
        permission.setType(0);
        permission.setStatus(1);
        if (permission.getId() == null) {
            permissionService.save(permission);
        }else {
            permissionService.updateById(permission);
        }

        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:list";
    }

    @RequestMapping("checkPermissionValue")
    @ResponseBody
    public boolean checkPermissionValueValid(@RequestParam String permissionValue) {
        return permissionService.findByPermissionValue(permissionValue) == null;
    }

    @RequiresPermissions("system:permission")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public Message delete(Long[] ids) {
        permissionService.deleteBatchIds(ids);
        return SUCCESS_MESSAGE;
    }
}
