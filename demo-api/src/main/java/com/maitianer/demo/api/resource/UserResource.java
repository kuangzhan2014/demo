package com.maitianer.demo.api.resource;

import com.maitianer.demo.api.resource.vm.MemberVM;
import com.maitianer.demo.api.security.CurrentUser;
import com.maitianer.demo.api.security.JWTTokenProvider;
import com.maitianer.demo.biz.service.MemberService;
import com.maitianer.demo.common.errors.BadRequestAlertException;
import com.maitianer.demo.common.utils.crypt.Encrypt;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.model.res.MemberRES;
import com.maitianer.demo.model.sys.Member;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Chen
 * @Date 2019/8/29 17:13
 */
@Api(tags = "用户资源")
@RestController
@RequestMapping("users")
public class UserResource {

    @Autowired
    private MemberService memberService;
    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @ApiOperation("当前用户信息")
    @GetMapping
    public MemberRES currentUser(@CurrentUser Member member) {
        MemberRES memberRES = new MemberRES();
        BeanUtils.copyProperties(member,memberRES);
        return memberRES;
    }

    @ApiOperation("登录")
    @PostMapping("login")
    public String login(MemberVM memberVM) {
        Member member = memberService.findByMemberName(memberVM.getUsername());
        if(null == member){
            throw new BadRequestAlertException("账号错误");
        }
        if(!member.getEncryptedPassword().equalsIgnoreCase(Encrypt.SHA256(memberVM.getPassword()))){
            throw new BadRequestAlertException("密码错误");
        }
        if(DomainConstants.MEMBER_STATUS_LOCKED == member.getStatus()){
            throw new BadRequestAlertException("账号被锁定");
        }
        if(DomainConstants.MEMBER_STATUS_DELETE == member.getStatus()){
            throw new BadRequestAlertException("账号被删除");
        }
        return jwtTokenProvider.createToken(member.getId(),member.getPhone(),true);
    }
}
