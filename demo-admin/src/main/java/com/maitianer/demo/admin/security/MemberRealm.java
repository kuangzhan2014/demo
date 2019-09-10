package com.maitianer.demo.admin.security;

import com.maitianer.demo.admin.security.token.CustomUsernamePasswordToken;
import com.maitianer.demo.biz.service.MemberService;
import com.maitianer.demo.biz.utils.SpringContextUtils;
import com.maitianer.demo.model.DomainConstants;
import com.maitianer.demo.model.sys.Member;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * User: Leo
 * Date: 16/4/9 下午4:16
 */
public class MemberRealm extends AuthorizingRealm implements InitializingBean {

    @Autowired
    private MemberService memberService;

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(MemberRealm.class);

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken || token instanceof CustomUsernamePasswordToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        CustomUsernamePasswordToken customUsernamePasswordToken = (CustomUsernamePasswordToken) token;
        String loginName = customUsernamePasswordToken.getUsername();
        Member member = memberService.findByMemberName(loginName);
        Long brandId = customUsernamePasswordToken.getBrandId();
        if (member == null) {
            throw new UnknownAccountException();
        }

        if(DomainConstants.MEMBER_STATUS_LOCKED == member.getStatus()){
            throw new LockedAccountException();
        }
        MemberSession memberSession = new MemberSession(member.getId(), member.getMemberName(), brandId, SpringContextUtils.getActiveProfile());

        return new SimpleAuthenticationInfo(memberSession, member.getEncryptedPassword(), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        MemberSession memberSession = (MemberSession) principals.fromRealm(getName()).iterator().next();
        if (memberSession != null) {
            Member member = memberService.getById(memberSession.getMemberId());
            List<String> stringPermissions = memberService.getStringPermissions(member.getId());
            if (stringPermissions != null) {
                SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
                authorizationInfo.addStringPermissions(stringPermissions);
                return authorizationInfo;
            }
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        CredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher(Sha256Hash.ALGORITHM_NAME);
        setCredentialsMatcher(credentialsMatcher);
    }
}
