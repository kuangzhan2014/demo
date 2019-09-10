package com.maitianer.demo.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maitianer.demo.model.common.ResultData;
import com.maitianer.demo.model.query.MemberQO;
import com.maitianer.demo.model.sys.Member;

import java.util.List;


/**
 * 系统成员业务
 * @author cosmo2097
 */
public interface MemberService extends IService<Member> {

    /**
     * 分页查询分类列表
     */
    IPage<Member> pageDate(Page<Member> pageRequest, MemberQO memberQO);

    boolean deleteBatchIds(Long[] ids);

    /**
     * 根据用户名查找用户
     * @param memberName
     * @return
     */
    Member findByMemberName(String memberName);

    /**
     * 更具手机号码查找用户
     * @param cellphone
     * @return
     */
    Member findByCellphone(String cellphone);

    /**
     * 根据邮箱查找用户
     * @param email
     * @return
     */
    Member findByEmail(String email);

    /**
     * 获得用户的权限值
     * @param memberId
     * @return
     */
    List<String> getStringPermissions(Long memberId);

    /**
     * 检查用户的密码是否正确
     * @param memberId
     * @param password
     * @return
     */
    boolean verifyPassword(Long memberId, String password);

    /**
     * 创建用户
     * @param member
     * @param password
     * @param roleId
     * @return
     */
    Member createMember(Member member, String password, Long roleId);

    /**
     * 更新用户
     * @param member
     * @param password
     * @param roleId
     * @return
     */
    Member updateMember(Member member, String password, Long roleId);

    /**
     * 功能描述: 根据memberId查询member
     * @Param: memberId
     * @Return: com.maitianer.demo.model.sys.Member
     */
    Member getMemberById(Long memberId);

    boolean deleteData(Long id);

    //通过邮箱验证是否存在用户
    Member forget(Member member);

    ResultData sendResetPassword(Member member, String resetPasseord);

    /**
     * 是否是管理员
     * @param memberId
     * @return
     */
    boolean isAdminAccount(Long memberId);
}
