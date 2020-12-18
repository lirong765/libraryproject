package com.rong.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rong.dao.BookInfoMapper;
import com.rong.dao.LendListMapper;
import com.rong.pojo.BookInfo;
import com.rong.pojo.LendList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("lendListService")
public class LendListServiceImpl implements LendListService {
    @Autowired
    private LendListMapper lendListDao;
    @Autowired
    private BookInfoMapper bookInfoDao;

    @Override
    public PageInfo<LendList> queryLendListAll(LendList lendList, int page, int limit) {
        PageHelper.startPage(page, limit);
        List<LendList> list = lendListDao.queryLendListAll(lendList);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public void andLendListSubmit(LendList lendList) {
        lendListDao.insert(lendList);
    }

    @Override
    public int queryBookNum(int readerId) {
        return lendListDao.queryLendNumberById(readerId);
    }

    @Override
    public int deleteLendListByIds(List<String> ids, List<String> bookIds) {
        int num = 0;

        //删除借阅记录
        for (String id : ids) {
            num += lendListDao.deleteLendListById(Integer.parseInt(id));

        }
        //更改图书标识，更新状态为未借出
        for (String bid : bookIds) {
            //根据id查询图书记录信息
            BookInfo bookInfo = bookInfoDao.selectByPrimaryKey(Integer.valueOf(bid));
            bookInfo.setStatus(0);   //数据库中的status改为未借出状态
            bookInfoDao.updateByPrimaryKeyWithBLOBs(bookInfo);
        }

        return num;
    }

    @Override
    public int updateLendListSubmit(List<String> ids, List<String> bookIds) {
        int num = 0;
        for (String id : ids) {
            //根据id查询借阅记录信息
            LendList lendList = new LendList();
            lendList.setId(Integer.valueOf(id));
            lendList.setBackDate(new Date());
            lendList.setType(0);//正常还书
            num += lendListDao.updateLendListSubmit(lendList);
        }

        //修改书的状态
        //更改图书标识，更新状态为未借出
        for (String bid : bookIds) {
            //根据id查询图书记录信息
            BookInfo bookInfo = bookInfoDao.selectByPrimaryKey(Integer.valueOf(bid));
            bookInfo.setStatus(0);   //数据库中的status改为未借出状态
            bookInfoDao.updateByPrimaryKeyWithBLOBs(bookInfo);
        }

        return num;
    }

    @Override
    public void backBook(LendList lendList) {
        LendList lend = new LendList();
        lendList.setBackDate(new Date());
        lend.setId(lendList.getId());
        lend.setType(lendList.getType());
        lend.setRemarks(lendList.getRemarks());
        lend.setBookId(lendList.getBookId());

        lendListDao.updateLendListSubmit(lendList);

        //判断异常还书  如果延期或者正常还书，需要更改书的状态
        if(lend.getType()==0 || lend.getType()==1){
            BookInfo bookInfo = bookInfoDao.selectByPrimaryKey(Integer.valueOf(lend.getBookId()));
            bookInfo.setStatus(0);   //数据库中的status改为未借出状态
            bookInfoDao.updateByPrimaryKeyWithBLOBs(bookInfo);
        }

    }

    @Override
    public List<LendList> queryLookBookList(Integer rid, Integer bid) {
        return lendListDao.queryLookBookList(rid, bid);
    }
}
