package com.maitianer.demo.common.errors;

import org.apache.commons.lang3.StringUtils;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Chen
 * @Date 2019/5/20 15:24
 */
public class JsonResponAlertException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    private final String errorKey;

    public JsonResponAlertException(String message) {
        this(message, null);
    }

    public JsonResponAlertException(String defaultMessage, String errorKey) {
        this(ErrorConstants.DEFAULT_TYPE, defaultMessage, errorKey);
    }

    public JsonResponAlertException(URI type, String defaultMessage, String errorKey) {
        super(type, defaultMessage, Status.BAD_REQUEST, null, null, null, getAlertParameters(errorKey));
        this.errorKey = errorKey;
    }

    private static Map<String, Object> getAlertParameters(String errorKey) {
        Map<String, Object> parameters = new HashMap<>();
        if (StringUtils.isNotBlank(errorKey)) {
            parameters.put("message", "error." + errorKey);
        }
        return parameters;
    }

    public String getErrorKey() {
        return errorKey;
    }
}
