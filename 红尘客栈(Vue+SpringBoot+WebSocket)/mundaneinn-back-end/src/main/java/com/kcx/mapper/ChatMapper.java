package com.kcx.mapper;

import com.kcx.pojo.Chat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ChatMapper {
    /*得到当前用户的所有聊天记录的最近一条*/
    List<Chat> queryUserChatList(@Param("username") String username);
    /*得到两个用户之间的聊天*/
    List<Chat> queryChatList(@Param("username1") String username1,@Param("username2") String username2);
    /*添加聊天记录*/
    Integer addChat(Chat chat);
}
