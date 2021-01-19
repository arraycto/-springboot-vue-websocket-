package com.kcx;

import com.kcx.mapper.ChatMapper;
import com.kcx.pojo.Chat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class ChatTest {
    @Autowired
    ChatMapper chatMapper;
    @Test
    public void queryUserChatList(){
        System.out.println(chatMapper.queryUserChatList("17365560614"));
    }
    @Test
    public void queryChatList(){
        System.out.println(chatMapper.queryChatList("17365560614","13522115666"));
    }
    @Test
    public void addChat(){
        Chat chat=new Chat(null,"17365560614","cs","测试聊天",new Date());
        System.out.println(chatMapper.addChat(chat));
    }
}
