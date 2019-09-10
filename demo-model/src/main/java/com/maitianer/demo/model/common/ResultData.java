package com.maitianer.demo.model.common;

/**
 * @Author yuzhe
 * @Date 2019/4/28 17:35:33
 **/
public class ResultData<T> {

    private Integer code;
    private T data;
    private String msg;

    public ResultData() {
        this.code = 0;
    }

    public ResultData(T data) {
        this.data = data;
        if(data == null) {
            this.code = 1;
        } else {
            this.code = 0;
        }
    }

    public ResultData(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResultData(T data, String msg) {
        this.data = data;
        this.msg = msg;
        if(data == null) {
            this.code = 1;
        } else {
            this.code = 0;
        }
    }

    public ResultData(T data, String successMsg, String failureMsg) {
        this.data = data;
        if(data == null) {
            this.code = 1;
            this.msg = failureMsg;
        } else {
            this.code = 0;
            this.msg = successMsg;
        }
    }

    public ResultData(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
