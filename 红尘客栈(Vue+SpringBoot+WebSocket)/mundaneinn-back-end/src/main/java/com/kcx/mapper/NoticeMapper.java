package com.kcx.mapper;

import com.kcx.pojo.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NoticeMapper {
    //查询用户通知
    List<Notice> queryUserNotice(@Param("username")String username);
    //添加通知
    Integer addNotice(Notice notice);
    //删除通知
    Integer deleteNotice(@Param("id")Integer id);
}
