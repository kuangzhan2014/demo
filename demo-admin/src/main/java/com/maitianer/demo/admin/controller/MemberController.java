package com.maitianer.demo.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maitianer.demo.admin.security.CurrentMember;
import com.maitianer.demo.admin.util.EncryptPassword;
import com.maitianer.demo.admin.util.MemberUtils;
import com.maitianer.demo.biz.service.MemberService;
import com.maitianer.demo.biz.service.RoleService;
import com.maitianer.demo.biz.utils.MessageUtils;
import com.maitianer.demo.common.utils.lang.StringUtils;
import com.maitianer.demo.common.web.Message;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.model.common.DataTableRequest;
import com.maitianer.demo.model.common.DataTableResponse;
import com.maitianer.demo.model.common.ResultData;
import com.maitianer.demo.model.query.MemberQO;
import com.maitianer.demo.model.sys.Member;
import com.maitianer.demo.model.sys.Role;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Date: 2017/8/24 下午10:39
 * @author cosmo2097
 */
@Controller("sysMemberController")
@RequestMapping("sys/member")
public class MemberController extends BaseController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private RoleService roleService;

    @ModelAttribute("roles")
    public List<Role> preloadRoles() {
        return roleService.list(null);
    }

    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public String profile(Model model, @CurrentMember Member member) {
        model.addAttribute("bean", MemberUtils.getCurrentMember());
        return "sys/member/profile";
    }

    @RequestMapping(value = "profile", method = RequestMethod.POST)
    public String saveProfile(Member bean, RedirectAttributes redirectAttributes) {
        Member member = MemberUtils.getCurrentMember();
        member.setRealName(bean.getRealName());
        member.setCellphone(bean.getCellphone());
        member.setEmail(bean.getEmail());
        member.setPhone(bean.getPhone());
        memberService.updateById(member);
        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:profile";
    }

    @RequestMapping(value = "changePassword", method = RequestMethod.GET)
    public String changePassword() {
        return "sys/member/changePassword";
    }

    @RequestMapping(value = "changePassword", method = RequestMethod.POST)
    public String doChangePassword(String oldPassword, String newPassword, RedirectAttributes redirectAttributes) {

        Member member = MemberUtils.getCurrentMember();

        // 检查旧密码
        if (!memberService.verifyPassword(member.getId(),EncryptPassword.encryptedPassword(oldPassword))) {
            addFlashMessage(redirectAttributes, Message.warn("旧密码不正确！"));
            return "redirect:profile";
        }

        // 保存新密码
        member.setEncryptedPassword(EncryptPassword.encryptedPassword(newPassword));
        memberService.updateById(member);

        addFlashMessage(redirectAttributes, Message.success("密码修改成功！"));
        return "redirect:changePassword";
    }

    @ResponseBody
    @GetMapping("pageData")
    public DataTableResponse<Member> pageData(DataTableRequest<Member> pageRequest,String searchProperty,String searchValue) {
        QueryWrapper<Member> wrapper = new QueryWrapper<Member>().eq("status",DomainConstants.MEMBER_STATUS_NORMAL);
        if (StringUtils.isNotBlank(searchProperty) && StringUtils.isNotBlank(searchValue)) {
            wrapper.like(searchProperty, searchValue);
        }
        IPage<Member> page = memberService.page(pageRequest, wrapper);
        DataTableResponse<Member> dataTableResponse = new DataTableResponse<Member>().success(page);
        return dataTableResponse;
    }

    @RequiresPermissions("system:member")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model, MemberQO memberQO,String searchProperty,String searchValue) {
        model.addAttribute("searchProperty", searchProperty);
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("bean",memberQO);
        return "sys/member/list";
    }

//    @RequiresPermissions("system:member")
//    @RequestMapping(value = "list", method = RequestMethod.GET)
//    public String list(Page<Member> pageRequest, String searchProperty, String searchValue, Model model) {
//        QueryWrapper<Member> wrapper = new QueryWrapper<Member>().eq("status",DomainConstants.MEMBER_STATUS_NORMAL);
//        if (StringUtils.isNotBlank(searchProperty) && StringUtils.isNotBlank(searchValue)) {
//            wrapper.like(searchProperty, searchValue);
//        }
//        IPage<Member> page = memberService.page(pageRequest, wrapper);
//        model.addAttribute("page", page);
//        model.addAttribute("searchProperty", searchProperty);
//        model.addAttribute("searchValue", searchValue);
//        return "sys/member/list";
//    }


    @RequiresPermissions("system:member")
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("roleId",2);
        Member member = new Member();
        member.setStatus(DomainConstants.MEMBER_STATUS_NORMAL);
        model.addAttribute("bean",member);
        return "sys/member/form";
    }

    @RequiresPermissions("system:member")
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(@RequestParam Long id, Model model) {
        Member member = memberService.getMemberById(id);

        List<Role> roles = roleService.findByMemberId(id);
        Role firstRole = roles.size() > 0 ? roles.get(0) : null;
        if (firstRole != null) {
            model.addAttribute("roleId", firstRole.getId());
        }
        model.addAttribute("bean", member);
        return "sys/member/form";
    }

    // TODO 处理错误返回
    @RequiresPermissions("system:member")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(Member member, Long organizationId, Long roleId,
                       String password, String passwordConfirmation, RedirectAttributes redirectAttributes) {
        if (member.getId() == null) {
            if (StringUtils.isBlank(password)) {
                addFlashMessage(redirectAttributes, Message.error("密码不能为空！"));
                return ERROR_VIEW;
            }
            if (!password.equals(passwordConfirmation)) {
                addFlashMessage(redirectAttributes, Message.error("确认密码不匹配！"));
                return ERROR_VIEW;
            }
            if (StringUtils.isNotBlank(password)) {
                member.setEncryptedPassword(EncryptPassword.encryptedPassword(password));
            }
            memberService.createMember(member, password, roleId);
        }else {
            if (StringUtils.isNotBlank(password)) {
                if (!password.equals(passwordConfirmation)) {
                    addFlashMessage(redirectAttributes, Message.error("确认密码不匹配！"));
                    return ERROR_VIEW;
                }
            }
            if (StringUtils.isNotBlank(password)) {
                member.setEncryptedPassword(EncryptPassword.encryptedPassword(password));
            }

            memberService.updateMember(member, password, roleId);
        }
        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:list";
    }

    @GetMapping(value = "checkMemberName")
    @ResponseBody
    public boolean checkMemberNameValid(Long id, String memberName) {
        QueryWrapper<Member> query = new QueryWrapper<>();
        if(StringUtils.isBlank(memberName)){
            return true;
        }
        if (null != id && id > 0){
            query.ne("id",id);
        }
        query.eq("member_name", memberName);
        query.eq("status", DomainConstants.MEMBER_STATUS_NORMAL);
        return memberService.list(query).size() > 0 ? false : true;
    }

    @GetMapping(value = "checkCellphone")
    @ResponseBody
    public boolean checkCellphoneValid(Long id,String cellphone) {
        QueryWrapper<Member> query = new QueryWrapper<>();
        if(StringUtils.isBlank(cellphone)){
            return true;
        }
        if (null != id && id > 0){
            query.ne("id",id);
        }
        query.eq("cellphone", cellphone);
        query.eq("status", DomainConstants.MEMBER_STATUS_NORMAL);
        return memberService.list(query).size() > 0 ? false : true;
    }

    @RequestMapping(value = "checkEmail")
    @ResponseBody
    public boolean checkEmailValid(Long id,String email) {
        QueryWrapper<Member> query = new QueryWrapper<>();
        if(StringUtils.isBlank(email)){
            return true;
        }
        if (null != id && id > 0){
            query.ne("id",id);
        }
        query.eq("email", email);
        query.eq("status", DomainConstants.MEMBER_STATUS_NORMAL);
        return memberService.list(query).size() > 0 ? false : true;
    }

    @RequiresPermissions("system:member")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public Message delete(Long[] ids) {
        memberService.deleteBatchIds(ids);
        return SUCCESS_MESSAGE;
    }

    @RequiresPermissions("system:member")
    @RequestMapping(value = "deleteData", method = RequestMethod.POST)
    @ResponseBody
    public ResultData deleteData(Long id) {
        boolean result = memberService.deleteData(id);
        return new ResultData(result ? 0 : 1, result);
    }

    @PostMapping("forget")
    @ResponseBody
    public ResultData forget(@RequestBody Member memberForm) {
        Member member = memberService.findByEmail(memberForm.getEmail());
        if (member != null) {
            String resetPassword =StringUtils.getRandomStr(10);
            member.setEncryptedPassword(EncryptPassword.encryptedPassword(resetPassword));
            memberService.updateById(member);
            return memberService.sendResetPassword(member,resetPassword);
        } else {
            return new ResultData<>(DomainConstants.ResultDataCode.FAIL, MessageUtils.get("UnknownEmailException"));
        }
    }
}
