package com.rong.dao;

import com.rong.pojo.ClassInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @component （把普通pojo实例化到spring容器中，相当于配置文件中的
 * <bean id="" class=""/>
 */
@Component("classDao")
public interface ClassInfoDao {

    /**
     *  查询所有的图书类型信息
     */
//    @Select("select * from class_info")
//    List<ClassInfo> queryClassInfoAll();

    List<ClassInfo> queryClassInfoAll(@Param(value = "name") String name);

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
    void deleteTypeByIds(List<Integer> ids);


}
