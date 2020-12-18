package com.rong.controller;

import com.github.pagehelper.PageInfo;
import com.rong.pojo.ClassInfo;
import com.rong.service.ClassInfoService;
import com.rong.service.ClassInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @Autowired
    private ClassInfoService classInfoService;

    @RequestMapping("/index")
    public String testIndex(){

        return "index";
    }
}
