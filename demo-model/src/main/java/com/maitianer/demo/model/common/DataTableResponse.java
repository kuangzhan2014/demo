package com.maitianer.demo.model.common;


import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * UserDTO: Leo
 * Date: 2018/3/26 下午5:22
 */
public class DataTableResponse<T> {

    private static final Integer SUCCESS_CODE = 0;
    private static final String SUCCESS_MESSAGE = "SUCCESS";

    private static final Integer FAIL_CODE = 1;
    private static final String FAIL_MESSAGE = "FAIL";

    private Integer code;
    private String msg;
    private Long count;
    private List<T> data;

    public DataTableResponse<T> success(IPage<T> page) {
        this.code = SUCCESS_CODE;
        this.msg = SUCCESS_MESSAGE;
        this.count = page.getTotal();
        this.data = page.getRecords();
        return this;
    }

    public DataTableResponse<T> success(List<T> list) {
        this.code = SUCCESS_CODE;
        this.msg = SUCCESS_MESSAGE;
        this.count = (long)list.size();
        this.data = list;
        return this;
    }

    public DataTableResponse<T> fail() {
        this.code = FAIL_CODE;
        this.msg = FAIL_MESSAGE;
        return this;
    }

    public DataTableResponse<T> fail(String message) {
        fail();
        this.msg = message;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public DataTableResponse<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public DataTableResponse<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Long getCount() {
        return count;
    }

    public DataTableResponse<T> setCount(Long count) {
        this.count = count;
        return this;
    }

    public List<T> getData() {
        return data;
    }

    public DataTableResponse<T> setData(List<T> data) {
        this.data = data;
        return this;
    }
}
