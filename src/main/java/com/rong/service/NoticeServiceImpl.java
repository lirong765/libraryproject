package com.rong.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rong.dao.NoticeMapper;
import com.rong.pojo.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeDao;

    @Override
    public PageInfo<Notice> queryNoticeAll(String content, int page, int limit) {
        PageHelper.startPage(page, limit);
        List<Notice> list = noticeDao.queryNoticeAll(content);
        PageInfo<Notice> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public void insertNoticeInfo(Notice notice) {
        noticeDao.insert(notice);
    }

    @Override
    public Notice queryNoticeById(Integer id) {
        System.out.println(noticeDao.queryNoticeById(id).getContent());
        System.out.println(noticeDao.queryNoticeById(id).getAuthor());
        System.out.println(noticeDao.queryNoticeById(id).getCreateDate());
        return noticeDao.queryNoticeById(id);
    }

    @Override
    public void deleteNoticeByIds(List<String> ids) {
        List<Integer> list = new ArrayList<>();
        for (String id:
             ids) {
            list.add(Integer.valueOf(id));
        }
        noticeDao.deleteNoticeByIds(list);
    }
}
