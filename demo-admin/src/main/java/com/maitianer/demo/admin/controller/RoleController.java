package com.maitianer.demo.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maitianer.demo.biz.service.PermissionService;
import com.maitianer.demo.biz.service.RoleService;
import com.maitianer.demo.common.utils.lang.StringUtils;
import com.maitianer.demo.common.web.Message;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.model.common.DataTableRequest;
import com.maitianer.demo.model.common.DataTableResponse;
import com.maitianer.demo.model.common.ResultData;
import com.maitianer.demo.model.query.RoleQO;
import com.maitianer.demo.model.sys.Role;
import com.maitianer.demo.model.sys.Permission;
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
    public String list(Model model, RoleQO roleQO, String searchProperty, String searchValue) {
        model.addAttribute("searchProperty", searchProperty);
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("bean",roleQO);
        return "sys/role/list";
    }

//    @RequiresPermissions("system:role")
//    @RequestMapping(value = "list", method = RequestMethod.GET)
//    public String list(Page<Role> pageRequest, String searchProperty, String searchValue, Model model) {
//        QueryWrapper<Role> wrapper = new QueryWrapper<>();
//        if (StringUtils.isNotBlank(searchProperty) && StringUtils.isNotBlank(searchValue)) {
//            wrapper.like(searchProperty, searchValue);
//        }
//        IPage<Role> page = roleService.page(pageRequest, wrapper);
//        model.addAttribute("page", page);
//        model.addAttribute("searchProperty", searchProperty);
//        model.addAttribute("searchValue", searchValue);
//        return "sys/role/list";
//    }

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
    public boolean checkCodeValid(Long id,String code) {
        QueryWrapper<Role> query = new QueryWrapper<>();
        if(StringUtils.isBlank(code)){
            return true;
        }
        if (null != id && id > 0){
            query.ne("id",id);
        }
        query.eq("code", code);
        query.eq("status", DomainConstants.ROLE_STATUS_NORMAL);
        return roleService.list(query).size() > 0 ? false : true;
    }

    @RequiresPermissions("system:role")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public Message delete(Long[] ids) {
        roleService.deleteBatchIds(ids);
        return SUCCESS_MESSAGE;
    }

    @ResponseBody
    @GetMapping("pageData")
    public DataTableResponse<Role> pageData(DataTableRequest<Role> pageRequest, String searchProperty, String searchValue) {
        QueryWrapper<Role> wrapper = new QueryWrapper<Role>().eq("status",DomainConstants.ROLE_STATUS_NORMAL);
        if (StringUtils.isNotBlank(searchProperty) && StringUtils.isNotBlank(searchValue)) {
            wrapper.like(searchProperty, searchValue);
        }
        IPage<Role> page = roleService.page(pageRequest, wrapper);
        DataTableResponse<Role> dataTableResponse = new DataTableResponse<Role>().success(page);
        return dataTableResponse;
    }

    @RequiresPermissions("system:role")
    @RequestMapping(value = "deleteData", method = RequestMethod.POST)
    @ResponseBody
    public ResultData deleteData(Long id) {
        boolean result = roleService.deleteData(id);
        return new ResultData(result ? 0 : 1, result);
    }
}
