package com.kcx.controller;

import com.kcx.mapper.ChatMapper;
import com.kcx.pojo.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    ChatMapper chatMapper;
    /*得到用户当前所有的聊天列表，即其他用户给他发的最后一条消息，类似qq*/
    @GetMapping("/getUserChatList/{username}")
    public List<Chat> getUserChatList(@PathVariable("username")String username){
        return chatMapper.queryUserChatList(username);
    }
    /*得到两个用户之间的聊天记录*/
    @GetMapping("/getChatList/{username1}/{username2}")
    public List<Chat> getChatList(@PathVariable("username1")String username1,@PathVariable("username2")String username2){
        return chatMapper.queryChatList(username1,username2);
    }
    /*添加聊天记录*/
    @PostMapping("/addChat")
    public void addChat(@RequestBody Chat chat){
        chat.setTime(new Date());
        chatMapper.addChat(chat);
    }
}
