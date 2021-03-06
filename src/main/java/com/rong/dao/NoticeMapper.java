package com.rong.dao;

import com.rong.pojo.Notice;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("noticeDao")
public interface NoticeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notice
     *
     * @mbggenerated Fri Nov 13 20:30:34 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notice
     *
     * @mbggenerated Fri Nov 13 20:30:34 CST 2020
     */
    int insert(Notice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notice
     *
     * @mbggenerated Fri Nov 13 20:30:34 CST 2020
     */
    int insertSelective(Notice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notice
     *
     * @mbggenerated Fri Nov 13 20:30:34 CST 2020
     */
    Notice selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notice
     *
     * @mbggenerated Fri Nov 13 20:30:34 CST 2020
     */
    int updateByPrimaryKeySelective(Notice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notice
     *
     * @mbggenerated Fri Nov 13 20:30:34 CST 2020
     */
    int updateByPrimaryKeyWithBLOBs(Notice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notice
     *
     * @mbggenerated Fri Nov 13 20:30:34 CST 2020
     */
    int updateByPrimaryKey(Notice record);

    /**
     * 查询公告信息（通过关键字）
     */
    List<Notice> queryNoticeAll(String content);

    /**
     * 公告删除（通过id链表)
     */
    void deleteNoticeByIds(List<Integer> ids);

    /**
     * 查询详细信息根据id查询
     */
    Notice queryNoticeById(Integer id);
}