package com.maitianer.demo.common.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * 身份认证异常
 * @Author: Mr.Zhang
 * @Date: 2018-09-20 09:51
 */
public class AuthenticationException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public AuthenticationException() {
        this("身份认证失败！");
    }

    public AuthenticationException(String message) {
        super(ErrorConstants.DEFAULT_TYPE, message, Status.UNAUTHORIZED);
    }
}
