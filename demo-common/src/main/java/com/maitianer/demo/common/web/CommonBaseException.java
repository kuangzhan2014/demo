package com.maitianer.demo.common.web;

/**
 * User: Leo
 * Date: 2018/2/16 下午8:09
 */
public class CommonBaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int code;

    public CommonBaseException() {
        super();
    }

    public CommonBaseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public CommonBaseException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
