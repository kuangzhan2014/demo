package com.maitianer.demo.biz.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maitianer.demo.biz.mapper.MemberMapper;
import com.maitianer.demo.biz.mapper.MemberRoleMapper;
import com.maitianer.demo.biz.service.*;
import com.maitianer.demo.biz.utils.MessageUtils;


import com.maitianer.demo.common.web.CommonBaseException;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.common.utils.lang.StringUtils;
import com.maitianer.demo.model.common.ResultData;
import com.maitianer.demo.model.data.MailBean;
import com.maitianer.demo.model.datatransfer.NoticeDTO;
import com.maitianer.demo.model.query.MemberQO;
import com.maitianer.demo.model.sys.Member;
import com.maitianer.demo.model.sys.MemberRole;
import com.maitianer.demo.model.sys.Permission;
import com.maitianer.demo.model.sys.Role;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author cosmo2097
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private MemberRoleMapper memberRoleMapper;

    @Autowired
    private MailService mailService;

    @Override
    public IPage<Member> pageDate(Page<Member> pageRequest, MemberQO memberQO) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        if(memberQO!=null){
            if (StringUtils.isNotBlank(memberQO.getMemberName())) {
                queryWrapper.like("m.member_name", memberQO.getMemberName());
            }
            if(StringUtils.isNotBlank(memberQO.getCellphone())){
                queryWrapper.like("m.cellphone",memberQO.getCellphone());
            }
            if (StringUtils.isNotBlank(memberQO.getEmail())) {
                queryWrapper.like("m.email", memberQO.getEmail());
            }
        }

        // 按字段排序
        if (StringUtils.isNotBlank(memberQO.getOrderField())) {
            queryWrapper.orderBy(true, memberQO.isAsc(),  StringUtils.toUnderScoreCase(memberQO.getOrderField()));
        }
        // 过滤已删除的公告
        queryWrapper.eq("m.status", DomainConstants.MEMBER_STATUS_NORMAL);
        IPage<Member> page = baseMapper.pageData(pageRequest, queryWrapper);
        return page;
    }

    @Override
    public boolean deleteBatchIds(Long[] ids) {
        for (Long id : ids) {
            if (DomainConstants.DEFAULT_SYSTEM_ADMIN_ID == id) {
                continue;
            }

            memberRoleMapper.delete(Wrappers.<MemberRole>lambdaQuery().eq(MemberRole::getMemberId, id));
        }
        return super.removeByIds(Arrays.asList(ids));
    }

    @Override
    public Member findByMemberName(String memberName) {
        return getOne(Wrappers.<Member>lambdaQuery().eq(Member::getMemberName, memberName), false);
    }

    @Override
    public Member findByCellphone(String cellphone) {
        return getOne(Wrappers.<Member>lambdaQuery().eq(Member::getCellphone, cellphone), false);
    }

    @Override
    public Member findByEmail(String email) {
        return getOne(Wrappers.<Member>lambdaQuery().eq(Member::getEmail, email), false);
    }

    @Override
    public List<String> getStringPermissions(Long memberId) {
        List<Role> roles = roleService.findByMemberId(memberId);
        List<String> stringPermissions = new ArrayList<>();
        for (Role role : roles) {
            List<Permission> permissions = permissionService.findByRoleId(role.getId());
            for (Permission permission : permissions) {
                if (!stringPermissions.contains(permission.getPermissionValue())) {
                    stringPermissions.add(permission.getPermissionValue());
                }
            }
        }
        return stringPermissions;
    }

    @Override
    public boolean verifyPassword(Long memberId, String password) {
        Assert.notNull(password, "password not null");
        Member member = getById(memberId);
        if (member == null) {
            return false;
        }
        return password.equals(member.getEncryptedPassword());
    }

    @Override
    public Member createMember(Member member, String password, Long roleId) {
        Assert.notNull(member, "member not null");

        member.setStatus(DomainConstants.MEMBER_STATUS_NORMAL);
        Role role = roleService.getById(roleId);
        if (role == null) {
            return null;
        }
        areaService.codeToAreaName(member);
        if (save(member)) {
            MemberRole memberRole = new MemberRole();
            memberRole.setMemberId(member.getId());
            memberRole.setRoleId(role.getId());
            memberRoleMapper.insert(memberRole);
        }

        return member;
    }

    @Override
    public Member updateMember(Member member, String password, Long roleId) {
        Assert.notNull(member, "member not null");

        areaService.codeToAreaName(member);
        Role role = roleService.getById(roleId);
        if (role == null) {
            return null;
        }
        if (updateById(member)) {
            MemberRole memberRole = new MemberRole();
            memberRole.setMemberId(member.getId());
            memberRole.setRoleId(roleId);
            memberRoleMapper.update(memberRole, new QueryWrapper<MemberRole>().eq("member_id", member.getId()));
        }

        return member;
    }

    /**
     * 功能描述:
     * @Param: [memberId]
     * @Return: com.maitianer.demo.model.sys.Member
     */
    @Override
    public Member getMemberById(Long memberId) {
        Member member = getById(memberId);

        member = areaService.selectCompletelArea(member);
        return member;
    }

    @Override
    public boolean deleteData(Long id) {
//        return removeById(id);
        Member member=getOne(new QueryWrapper<Member>().eq("id",id));
        if(DomainConstants.DEFAULT_SYSTEM_ADMIN_ID != id){
            member.setStatus(DomainConstants.MEMBER_STATUS_DELETE);
            memberRoleMapper.delete(Wrappers.<MemberRole>lambdaQuery().eq(MemberRole::getMemberId, id));
        }
        Boolean result = updateById(member);
        return result;
    }

    @Override
    public Member forget(Member member) {
        member = getOne(new QueryWrapper<Member>().eq("email", member.getEmail()));
        if(member==null){
            MessageUtils.get("UnknownEmailException");
        }
        return member;
    }

    @Override
    public ResultData sendResetPassword(Member member, String resetPassword) {
        MailBean mailBean=new MailBean();
        mailBean.setRecipient(member.getEmail());
        mailBean.setSubject(member.getMemberName()+": "+MessageUtils.get("resetPassword.response.success"));
        mailBean.setContent(MessageUtils.get("member.newPassword")+": "+resetPassword);
        if(mailService.sendSimpleMail(mailBean)) {
            return new ResultData<>(DomainConstants.ResultDataCode.SUCCESS);
        }else{
            return new ResultData<>(DomainConstants.ResultDataCode.FAIL,MessageUtils.get("sendEmail.response.fail"));

        }
    }

    @Override
    public boolean isAdminAccount(Long memberId) {
        List<Role> roles = roleService.findByMemberId(memberId);
        Role firstRole = roles.size() > 0 ? roles.get(0) : null;
        if(firstRole.getId() != 1){
            return false;
        }
        return true;
    }
}
