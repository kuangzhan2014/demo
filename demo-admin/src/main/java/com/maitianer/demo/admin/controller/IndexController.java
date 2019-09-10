package com.maitianer.demo.admin.controller;

import com.maitianer.demo.admin.util.MemberUtils;
import com.maitianer.demo.biz.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * User: Leo
 * Date: 2017/8/24 下午10:39
 */
@Controller("commonIndexController")
public class IndexController extends BaseController {

    @Autowired
    private MemberService memberService;

    @GetMapping({"/", "index"})
    public String index() {
        Long memberId = MemberUtils.getMemberSession().getMemberId();
        if(memberService.isAdminAccount(memberId)){
            return "redirect:/sys/indexFrame";
        }else{
            return "redirect:/brand/index";
        }
    }

    @GetMapping("sys/indexFrame")
    public String indexFrame() {
        return "indexFrame";
    }


    @GetMapping(value = "sys/dashboard")
    public String dashboard() {
        return "sys/dashboard";
    }

}
