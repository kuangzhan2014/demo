package com.maitianer.demo.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Mr.Zhang
 * @Date: 2019-08-19 16:49
 */
@RequestMapping("example")
@Controller
public class ExampleController {

    @GetMapping("contentIndex")
    public String contentIndex() {
        return "example/contentIndex";
    }

    @GetMapping("contentList")
    public String contentList() {
        return "example/contentList";
    }

    @GetMapping("contentDetail")
    public String contentDetail() {
        return "example/contentDetail";
    }
}
