package com.rong.service;

import com.github.pagehelper.PageInfo;
import com.rong.pojo.ClassInfo;

import java.util.List;

public interface ClassInfoService {
    /**
     *  查询所有的图书类型信息
     */
    //name为类别的名称，page为当前页，limit为每页限制多少条
    PageInfo<ClassInfo> queryClassInfoAll(String name, int page, int limit);

    /**
     * 添加图书类型
     */
    void addTypeSubmit(ClassInfo info);

    /**
     * 修改 根据id查询记录信息
     */
    ClassInfo queryClassInfoById(Integer id);

    /**
     * 修改图书类型
     */
    void updateTypeSubmit(ClassInfo info);

    /**
     * 根据ids（一个或多个id）删除记录信息
     */
    void deleteTypeByIds(List<Integer> id);
}
