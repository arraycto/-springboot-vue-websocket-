package com.kcx.controller;

import com.kcx.mapper.NoticeMapper;
import com.kcx.pojo.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    NoticeMapper noticeMapper;
    /*得到通知*/
    @GetMapping("/getNotice/{username}")
    public List<Notice> getNotice(@PathVariable("username") String username){
        return noticeMapper.queryUserNotice(username);
    }
    /*添加通知*/
    @PostMapping("/addNotice")
    public String addNotice(@RequestBody Notice notice){
        if(noticeMapper.addNotice(notice)==1){
            return "success";
        }
        return "error";
    }
}
