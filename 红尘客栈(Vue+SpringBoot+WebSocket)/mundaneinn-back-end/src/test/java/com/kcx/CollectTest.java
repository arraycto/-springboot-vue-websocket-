package com.kcx;

import com.kcx.mapper.CollectMapper;
import com.kcx.pojo.Collect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CollectTest {
    @Autowired
    CollectMapper collectMapper;

    @Test
    public void queryUserCollections(){
        System.out.println(collectMapper.queryUserCollections("17365560614"));
    }

    @Test
    public void addCollect(){
        Collect collect=new Collect(null,"17365560614","17655566666","测试收藏");
        System.out.println(collectMapper.addCollection(collect));
    }
    @Test
    public void deleteCollect(){
        System.out.println(collectMapper.deleteCollection(3));
    }
}
