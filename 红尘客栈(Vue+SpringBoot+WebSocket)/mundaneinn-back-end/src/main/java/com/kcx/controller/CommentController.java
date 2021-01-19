package com.kcx.controller;

import com.kcx.mapper.CommentMapper;
import com.kcx.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentMapper commentMapper;
    /*得到评论*/
    @GetMapping("/getCommentList/{username}/{title}/{page}/{pageSize}")
    public List<Comment> getCommentList(@PathVariable("username") String username,@PathVariable("title") String title,
                                    @PathVariable("page") Integer page, @PathVariable("pageSize") Integer pageSize){
        return commentMapper.queryComments(username,title,pageSize,(page-1)*pageSize);
    }
    /*得到评论数量*/
    @GetMapping("/getCommentTotal/{username}/{title}")
    public Integer getCommentTotal(@PathVariable("username") String username,@PathVariable("title") String title){
        return commentMapper.queryCommentsTotal(username,title);
    }
    /*添加评论*/
    @PostMapping("/addComment")
    public String addComment(@RequestBody Comment comment){
        comment.setCommentTime(new Date());
        //查询该用户是否已经评论过，评论过不能再次评论
        if(commentMapper.queryComment(comment.getUserId(),comment.getUsername(),comment.getTitle())==null&&
                commentMapper.addComment(comment)==1)
            return "success";
        return "error";
    }
}
