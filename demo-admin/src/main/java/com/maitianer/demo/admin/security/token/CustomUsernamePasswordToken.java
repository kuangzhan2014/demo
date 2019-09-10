package com.maitianer.demo.admin.security.token;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

/**
 * @Author yuzhe
 * @Date 2019/5/20 11:18:43
 **/
public class CustomUsernamePasswordToken implements HostAuthenticationToken, RememberMeAuthenticationToken {

    // 品牌Id
    private Long brandId;
    /**
     * The username
     */
    private String username;
    /**
     * The password, in char[] format
     */
    private String password;
    /**
     * Whether or not 'rememberMe' should be enabled for the corresponding login attempt;
     * default is <code>false</code>
     */
    private boolean rememberMe = false;
    /**
     * The location from where the login attempt occurs, or <code>null</code> if not known or explicitly
     * omitted.
     */
    private String host;

    public CustomUsernamePasswordToken() {
    }

    public CustomUsernamePasswordToken(String username, String password, Long brandId) {
        this.username = username;
        this.password = password;
        this.brandId = brandId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public boolean isRememberMe() {
        return false;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return getPassword();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
