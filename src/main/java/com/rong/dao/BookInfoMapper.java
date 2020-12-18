package com.rong.dao;

import com.rong.pojo.BookInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("bookInfoDao")
public interface BookInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_info
     *
     * @mbggenerated Fri Nov 13 20:30:34 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_info
     *
     * @mbggenerated Fri Nov 13 20:30:34 CST 2020
     */
    int insert(BookInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_info
     *
     * @mbggenerated Fri Nov 13 20:30:34 CST 2020
     */
    int insertSelective(BookInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_info
     *
     * @mbggenerated Fri Nov 13 20:30:34 CST 2020
     */
    BookInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_info
     *
     * @mbggenerated Fri Nov 13 20:30:34 CST 2020
     */
    int updateByPrimaryKeySelective(BookInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_info
     *
     * @mbggenerated Fri Nov 13 20:30:34 CST 2020
     */
    int updateByPrimaryKeyWithBLOBs(BookInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_info
     *
     * @mbggenerated Fri Nov 13 20:30:34 CST 2020
     */
    int updateByPrimaryKey(BookInfo record);

    /**
     * 查询所有图书信息
     */
    List<BookInfo> queryBookInfoAll(BookInfo info);

    /**
     * 查询状态为未借出（status=1）的所有图书信息
     */
    List<BookInfo> queryBookStatusIs1(BookInfo info);

    /**
     * 根据图书类型获取图书的数量
     */
    List<BookInfo> getBookCountByType();
}