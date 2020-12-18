package com.rong.controller;

import com.github.pagehelper.PageInfo;
import com.rong.pojo.Notice;
import com.rong.service.NoticeService;
import com.rong.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @GetMapping("/noticeIndex")     //请求管理员授权的公告界面
    public String noticeIndex(){
        return "notice/noticeIndex";
    }

    @GetMapping("/noticeIndex2")    //请求读者授权的公告界面
    public String noticeIndex2(){
        return "notice/noticeIndex2";
    }

    /**
     * 发布公告页面跳转
     */
    @GetMapping("/addNotice")
    public String addNotice(){
        return "notice/addNotice";
    }

    /**
     * 公告详情跳转
     */
    @GetMapping("/queryNoticeById")
    public String queryNoticeById(Integer id, Model model){
        //根据id查询公告详细信息
        Notice notice = noticeService.queryNoticeById(id);
        model.addAttribute("info", notice);

        return "notice/queryNotice";
    }

    /**
     * 查询所有的公告信息
     */
    @ResponseBody
    @RequestMapping("/noticeAll")
    public R noticeAll(String content, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "15") int limit){
        PageInfo<Notice> pageInfo = noticeService.queryNoticeAll(content, page, limit);

        return R.ok("成功", pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 公告发布提交方法
     */
    @ResponseBody
    @RequestMapping("/addNoticeSubmit")
    public R addNoticeSubmit(Notice notice){
        //设置进入
        notice.setAuthor(1);
        notice.setCreateDate(new Date());
        noticeService.insertNoticeInfo(notice);
        return R.ok();
    }

    /**
     * 根据ids删除公告信息
     */
    @ResponseBody
    @RequestMapping("/deleteNoticeByIds")
    public R deleteNoticeByIds(String ids){
        List list = Arrays.asList(ids.split(","));
        noticeService.deleteNoticeByIds(list);
        return R.ok();
    }
}
