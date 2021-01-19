package com.kcx;

import com.kcx.mapper.CommentMapper;
import com.kcx.pojo.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class CommentTest {
    @Autowired
    CommentMapper commentMapper;
    @Test
    void queryComments(){
        /*注意pagesize是每页显示的数量，而page是第几页，但page=每页显示的数量*（页数-1）*/
        System.out.println(commentMapper.queryComments("13522115666","一居室大床/超大观景阳台/近望京SOHO",2,0*2));
    }
    @Test
    void queryComment(){
        System.out.println(commentMapper.queryComment("173655606145","18111278167","青城之栖山中别墅【少城栖】"));
    }
    @Test
    void queryCommentsTotal(){
        System.out.println(commentMapper.queryCommentsTotal("18111278167","青城之栖山中别墅【少城栖】"));
    }
    @Test
    void addComment(){
        Comment comment=new Comment(null,"17365560614","18111278167","这是测试的评论",
                "很不错",new Date(),5,"http:xxxx");
        System.out.println(commentMapper.addComment(comment));
    }
}
