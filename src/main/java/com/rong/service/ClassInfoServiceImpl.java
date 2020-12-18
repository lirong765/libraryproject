package com.rong.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rong.dao.ClassInfoDao;
import com.rong.pojo.ClassInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("classInfoService")
public class ClassInfoServiceImpl implements ClassInfoService {
    @Autowired
    private ClassInfoDao classInfoDao;

    @Override
    public PageInfo<ClassInfo> queryClassInfoAll(String name, int page, int limit) {
        //传入参数，每页条数，当前页
        PageHelper.startPage(page, limit);
        List<ClassInfo> list = classInfoDao.queryClassInfoAll(name);

        //通过包装获取分页需要的其他值信息
        PageInfo<ClassInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public void addTypeSubmit(ClassInfo info) {
        System.out.println(info);
        classInfoDao.addTypeSubmit(info);
    }

    @Override
    public ClassInfo queryClassInfoById(Integer id) {
        return classInfoDao.queryClassInfoById(id);
    }

    @Override
    public void updateTypeSubmit(ClassInfo info) {
        classInfoDao.updateTypeSubmit(info);
    }

    @Override
    public void deleteTypeByIds(List<Integer> ids) {
        classInfoDao.deleteTypeByIds(ids);
    }

}

