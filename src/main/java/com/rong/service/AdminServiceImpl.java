package com.rong.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rong.dao.AdminMapper;
import com.rong.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adminService ")
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminDao;

    @Override
    public PageInfo<Admin> queryAdminInfoAll(Admin admin, int page, int limit) {
        PageHelper.startPage(page,limit);
        List<Admin> list = adminDao.queryAdminInfoAll(admin);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public int addAdminSubmit(Admin admin) {
        return adminDao.insert(admin);
    }

    @Override
    public int updateAdminSubmit(Admin admin) {
        return adminDao.updateByPrimaryKey(admin);
    }

    @Override
    public int deleteAdminByIds(List<String> ids) {
         int num = 0;   //记录删除的数量
         for(String id:ids){
             num+= adminDao.deleteByPrimaryKey(Integer.valueOf(id));
         }
        return num;
    }


    @Override
    public Admin queryAdminById(Integer id) {
        return adminDao.selectByPrimaryKey(id);
    }

    @Override
    public Admin queryUserByNameAndPassword(String username, String password) {
        return adminDao.queryUserByNameAndPassword(username, password);
    }
}
