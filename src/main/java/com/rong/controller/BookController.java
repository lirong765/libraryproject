package com.rong.controller;

import com.github.pagehelper.PageInfo;
import com.rong.pojo.BookInfo;
import com.rong.service.BookInfoService;
import com.rong.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookInfoService bookInfoService;
    /**
     * 图书首页
     */
    @GetMapping("/bookIndex")
    public String bookIndex(){
        return "book/bookIndex";
    }

    /**
     * 添加页面
     */
    @GetMapping("/addBook")
    public String addBook(){
        return "book/addBook";
    }

    /**
     * 实现添加功能
     */
    @ResponseBody
    @RequestMapping("/addBookSubmit")
    public R addBookSubmit(@RequestBody BookInfo bookInfo){   //@RequestBody作用：@RequestBody BookInfo bookInfo 这种形式会将JSON字符串中的值赋予bookInfo中对应的属性上，需要注意的是，JSON字符串中的key必须对应bookInfo中的属性名，否则是请求不过去的。
        bookInfoService.addBookSubmit(bookInfo);
        return R.ok();
    }

    /**
     * 获取图书信息
     */
    @ResponseBody
    @RequestMapping("/bookAll")
    public R bookAll(BookInfo info, @RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "15") int limit){

        PageInfo<BookInfo> pageInfo = bookInfoService.queryBookInfoAll(info, page, limit);
        return  R.ok("成功", pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 删除功能
     */
    @ResponseBody
    @RequestMapping("/deleteBookByIds")
    public R deleteBookByIds(String ids){
        List<String> list = Arrays.asList(ids.split(","));
        bookInfoService.deleteBookInfoById(list);
        return R.ok();
    }

    /**
     * 根据id查询记录信息
     */
    @GetMapping("/queryBookInfoById")
    public String queryBookInfoById(Model model, Integer id){
        //根据id获取信息
        BookInfo bookInfo = bookInfoService.queryBookInfoById(id);
        model.addAttribute("bookInfo", bookInfo );
        return "book/updateBook";
    }

    /**
     * 修改实现功能
     */
    @ResponseBody
    @RequestMapping("/updateBookInfoSubmit")
    public R updateBookInfoSubmit(@RequestBody BookInfo info){
        bookInfoService.updateBookSubmit(info);
        System.out.println("======================"+info.getId()+"==============="+info.getLanguage());
        return R.ok();
    }

}
