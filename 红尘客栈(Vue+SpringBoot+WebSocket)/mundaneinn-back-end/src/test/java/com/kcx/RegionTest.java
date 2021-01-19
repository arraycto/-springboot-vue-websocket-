package com.kcx;

import com.kcx.mapper.RegionMapper;
import com.kcx.pojo.Region;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

@SpringBootTest
public class RegionTest {
    @Autowired
    RegionMapper regionMapper;
    @Test
    void queryUserList(){
        HashMap map=new HashMap();
        map.put("cityName","北京");
        //map.put("type","hotsearch");
        List<Region> regions=regionMapper.queryRegionIf(map);
        System.out.println(regions);
    }
}
