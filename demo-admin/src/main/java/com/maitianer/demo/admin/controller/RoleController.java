package com.maitianer.demo.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maitianer.demo.biz.service.PermissionService;
import com.maitianer.demo.biz.service.RoleService;
import com.maitianer.demo.common.utils.lang.StringUtils;
import com.maitianer.demo.common.web.Message;
import com.maitianer.demo.model.sys.Permission;
import com.maitianer.demo.model.sys.Role;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Leo
 * Date: 2018/1/28 下午4:26
 */
@Controller("sysRoleController")
@RequestMapping("sys/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @ModelAttribute("rootPermissions")
    public List<Permission> preloadRootPermissions() {
        return permissionService.findRootPermissions();
    }

    @RequiresPermissions("system:role")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Page<Role> pageRequest, String searchProperty, String searchValue, Model model) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(searchProperty) && StringUtils.isNotBlank(searchValue)) {
            wrapper.like(searchProperty, searchValue);
        }
        IPage<Role> page = roleService.page(pageRequest, wrapper);
        model.addAttribute("page", page);
        model.addAttribute("searchProperty", searchProperty);
        model.addAttribute("searchValue", searchValue);
        return "sys/role/list";
    }

    @RequiresPermissions("system:role")
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("bean", new Role());
        model.addAttribute("stringPermissions", new ArrayList<>());
        return "sys/role/form";
    }

    @RequiresPermissions("system:role")
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(@RequestParam Long id, Model model) {
        List<Permission> permissions = permissionService.findByRoleId(id);
        List<String> stringPermissions = new ArrayList<>();
        for (Permission permission : permissions) {
            stringPermissions.add(permission.getPermissionValue());
        }
        model.addAttribute("bean", roleService.getById(id));
        model.addAttribute("stringPermissions", stringPermissions);
        return "sys/role/form";
    }

    @RequiresPermissions("system:role")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(Role role, Long[] permissionIds, RedirectAttributes redirectAttributes) {
        roleService.saveRole(role, permissionIds);
        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:list";
    }

    @RequestMapping(value = "checkCode")
    @ResponseBody
    public boolean checkMemberNameValid(@RequestParam String code) {
        return roleService.findByCode(code) == null;
    }

    @RequiresPermissions("system:role")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public Message delete(Long[] ids) {
        roleService.deleteBatchIds(ids);
        return SUCCESS_MESSAGE;
    }
}
