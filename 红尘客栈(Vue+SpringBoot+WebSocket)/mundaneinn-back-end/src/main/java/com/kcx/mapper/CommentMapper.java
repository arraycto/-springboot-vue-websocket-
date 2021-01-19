package com.kcx.mapper;

import com.kcx.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface CommentMapper {
    /*得到房屋评论列表*/
    List<Comment> queryComments(@Param("username")String username,@Param("title") String title,@Param("pageSize")
            Integer pageSize,@Param("page") Integer page);
    /*得到某个用户对某个房屋的评论*/
    Comment queryComment(@Param("userId")String userId,@Param("username")String username,@Param("title") String title);
    /*得到房屋评论数量*/
    Integer queryCommentsTotal(@Param("username")String username,@Param("title") String title);
    /*增加房屋评论*/
    Integer addComment(Comment comment);
}
