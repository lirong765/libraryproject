package com.rong.service;

import com.github.pagehelper.PageInfo;
import com.rong.pojo.BookInfo;

import java.util.List;

public interface BookInfoService {
    /**
     * 分页查询图书记录信息
     */
    PageInfo<BookInfo> queryBookInfoAll(BookInfo bookInfo, int page, int limit);

    /**
     * 添加
     */
    public void addBookSubmit(BookInfo info);

    /**
     * 修改图书
     */
    void updateBookSubmit(BookInfo info);

    /**
     * 根据id查询记录
     */
    BookInfo queryBookInfoById(Integer id);

    /**
     * 删除功能
     */
    void deleteBookInfoById(List<String> ids);

    /**
     * 查询状态为未借出（status=1）的所有图书信息
     */
    PageInfo<BookInfo> bookStatusIs1(BookInfo bookInfo, int page, int limit);

    /**
     * 根据图书类型获取图书的数量
     */
    List<BookInfo> getBookCountByType();
}
