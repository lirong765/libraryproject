package com.rong.service;

import com.github.pagehelper.PageInfo;
import com.rong.pojo.Notice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("noticeService")
public interface NoticeService {
    //分页查询
    PageInfo<Notice> queryNoticeAll(String content, int page, int limit);

    //添加公告
    void insertNoticeInfo(Notice notice);

    //查询根据id
    Notice queryNoticeById(Integer id);

    //删除
    void deleteNoticeByIds(List<String> ids);
}
