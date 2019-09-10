package com.maitianer.demo.common.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * @Author: Mr.Zhang
 * @Date: 2018-09-20 09:52
 */
public class AccessDeniedException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public AccessDeniedException(String message) {
        super(ErrorConstants.DEFAULT_TYPE, message, Status.FORBIDDEN);
    }
}
