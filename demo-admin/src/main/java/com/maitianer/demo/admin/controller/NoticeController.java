package com.maitianer.demo.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maitianer.demo.admin.security.MemberSession;
import com.maitianer.demo.admin.util.MemberUtils;
import com.maitianer.demo.biz.service.NoticeService;
import com.maitianer.demo.common.utils.HtmlToPdfUtils;
import com.maitianer.demo.model.common.DataTableRequest;
import com.maitianer.demo.model.common.DataTableResponse;
import com.maitianer.demo.model.common.ResultData;
import com.maitianer.demo.model.data.NoticeBean;
import com.maitianer.demo.model.datatransfer.NoticeDTO;
import com.maitianer.demo.model.query.NoticeQO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: zhou
 * @Date: 2019/08/21 09:15
 */
@Controller("noticeController")
@RequestMapping("notice")
public class NoticeController extends BaseController {
    @Autowired
    private NoticeService noticeService;

//    @RequiresPermissions("operation:notice")
    @GetMapping("list")
    public String list(Model model, NoticeQO noticeQO) {
        model.addAttribute("bean", noticeQO);
        return "notice/list";
    }

    @ResponseBody
    @GetMapping("pageData")
    public DataTableResponse<NoticeDTO> pageData(DataTableRequest<NoticeDTO> pageRequest, NoticeQO searchCondition) {
        MemberSession memberSession=MemberUtils.getMemberSession();
        Long brandId=memberSession.getCurrentBrandId();
        IPage<NoticeDTO> page = noticeService.pageData(pageRequest, searchCondition,brandId);
        DataTableResponse<NoticeDTO> dataTableResponse = new DataTableResponse<NoticeDTO>().success(page);
        return dataTableResponse;
    }


//    @RequiresPermissions("operation:notice")
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        MemberSession memberSession=MemberUtils.getMemberSession();
        NoticeDTO notice=new NoticeDTO();
        notice.setBrandId( memberSession.getCurrentBrandId());
        model.addAttribute("bean", notice);
        return "notice/form";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(NoticeDTO notice, RedirectAttributes redirectAttributes) {
        boolean result = noticeService.saveData(notice);
        addFlashMessage(redirectAttributes, result ? SUCCESS_MESSAGE : ERROR_MESSAGE);
        return "redirect:list";
    }

    @GetMapping(value = "detail")
    public String detail(@RequestParam Long id,Model model){
        NoticeDTO notice=noticeService.getData(id);
        model.addAttribute("bean",notice);
        return "notice/detail";
    }

//    @RequiresPermissions("operation:notice")
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(@RequestParam Long id, Model model) {
        NoticeDTO notice = noticeService.getData(id);
        model.addAttribute("bean", notice);
        return "notice/form";
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultData delete(Long id) {
        boolean result = noticeService.deleteData(id);
        return new ResultData(result ? 0 : 1, result);
    }


    @ResponseBody
    @GetMapping("download/{id}")
    public void download(@PathVariable Long id, HttpServletResponse response) {
        NoticeBean notice = noticeService.getById(id);
        //在下面，body中设置了style，设置了默认字体为宋体，这样导出时的html语言就默认带有了字体，汉字才会导出成功
        String content="<html><body style=\"font-family: 黑体, SimHei;\">" +
                "<p style=\"text-align: center;\"><span style=\"font-family: 黑体, SimHei; font-size: 24px;\">"
                + notice.getTitle() + "</span></p>" + notice.getContent() + "</body></html>";
        HtmlToPdfUtils.htmltoPdf(content,response);
    }

}
