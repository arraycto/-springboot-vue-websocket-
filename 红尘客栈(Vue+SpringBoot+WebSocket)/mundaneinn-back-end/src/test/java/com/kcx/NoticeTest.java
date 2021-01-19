package com.kcx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kcx.mapper.NoticeMapper;
import com.kcx.pojo.Notice;
import com.kcx.pojo.Region;
import com.kcx.utils.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
public class NoticeTest {

    @Autowired
    NoticeMapper noticeMapper;

    @Test
    void queryUserNotice(){
        System.out.println(noticeMapper.queryUserNotice("17365560614"));
    }
    @Test
    void addNotice(){
        Notice notice=new Notice(null,"ALL","system",new Date(),"测试通知","wwww.baidu.com");
        System.out.println(noticeMapper.addNotice(notice));
    }
    @Test
    void deleteNotice(){
        System.out.println(noticeMapper.deleteNotice(3));
    }
    @Test
    void dateTest() {
        System.out.println(JsonUtils.getJson(new Date()));
    }

}
