package com.rong.controller;

import com.github.pagehelper.PageInfo;
import com.rong.pojo.Admin;
import com.rong.service.AdminService;
import com.rong.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 查询管理员的首页
     */
    @GetMapping("/adminIndex")
    public String adminIndex() {
        return "admin/adminIndex";
    }

    /**
     * 添加管理员界面
     */
    @GetMapping("/addAdmin")
    public String addAdmin() {
        return "admin/addAdmin";
    }

    /**
     * 根据id查询管理员信息
     */
    @GetMapping("/queryAdminInfoById")
    public String queryAdminInfoById(Integer id, Model model) {
        model.addAttribute("id", id);
        return "admin/updateAdmin";
    }

    /**
     * 提交管理员添加功能实现
     */
    @ResponseBody
    @RequestMapping("/addAdminSubmit")
    public R addAdminSubmit(Admin admin) {
        int num = adminService.addAdminSubmit(admin);
        if (num > 0) {
            return R.ok();
        } else {
            return R.fail("添加失败");
        }
    }

    /**
     * 密码修改
     */
    @ResponseBody   //@responseBody注解的作用是将controller的方法返回的对象通过适当的转换器转换为指定的格式之后，写入到response对象的body区
    @RequestMapping("/updatePasswordSubmit")
    public R updatePasswordSubmit(Integer id, String oldPassword, String newPassword) {
        //根据id查询对象
        Admin info = adminService.queryAdminById(id);
        if (!oldPassword.equals(info.getPassword())) {     //输入的密码是否和原密码一致
            return R.fail("输入的旧密码和原来不一致");
        } else {
            info.setPassword(newPassword);
            adminService.updateAdminSubmit(info);
            return R.ok("修改密码成功");
        }
    }

    /**
     * 删除功能实现
     */
    @ResponseBody
    @RequestMapping("/deleteAdmin")
    public R deleteAdmin(String ids) {
        int num = adminService.deleteAdminByIds(Arrays.asList(ids.split(","))); //将ids转换为数组，然后将数组转换为List
        if (num > 0) {
            return R.ok();
        } else {
            return R.fail("删除失败");
        }
    }

    /**
     * 查询所有的管理员信息
     */
    @ResponseBody
    @RequestMapping("/adminAll")
    public R adminAll(Admin admin, @RequestParam(defaultValue = "1") int page,
                      @RequestParam(defaultValue = "15") int limit) {
        //查询所有的记录信息
        PageInfo<Admin> pageInfo = adminService.queryAdminInfoAll(admin, page, limit);
        return R.ok("成功", pageInfo.getTotal(), pageInfo.getList());
    }
}
