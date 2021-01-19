package com.kcx;

import com.kcx.mapper.HouseMapper;
import com.kcx.pojo.House;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class HouseTest {
    @Autowired
    HouseMapper houseMapper;
    @Test
    void queryHouseList(){
        /*注意pagesize是每页显示的数量，而page是第几页，但page=每页显示的数量*（页数-1）*/
        System.out.println(houseMapper.queryHouseList("成都",1,null,null,
                null,null,"2020-12-28,2020-12-29",2,0*2));
    }
    @Test
    void queryHouseTotal(){
        System.out.println(houseMapper.queryHouseTotal("北京",null,null,null,
                null,null,"2020-12-28,2020-12-29"));
/*        System.out.println(""=="");
        System.out.println("ccc"=="ccc");*/
    }
    @Test
    void queryHouse(){
        System.out.println(houseMapper.queryHouse("大同小居【田园花坊】loft观景大床房/自助入住/近宽窄巷子/春熙路","18002115666").get(0));
        //System.out.println(houseMapper.queryHouse(null,"18111278167"));
    }

    @Test
    void queryHouseByState(){
        System.out.println(houseMapper.queryHouseByAdmin(null,"成都","17365560614",null,6,0*6));
    }

    @Test
    void queryHouseTotalByState(){
        System.out.println(houseMapper.queryHouseTotalByAdmin(1,"成都","17365560614",null));
    }

    @Test
    void addHouse(){
/*                House house=new House(null,"【谷物烘焙室】 三里屯大使馆","complete", 1,2,1,BigDecimal.valueOf(0),
                0,"https:xxx","https://pic.tujia.com/upload/landlordunit/day_180416/thumb/201804161724089457_700_467.jpg," +
                "https://pic.tujia.com/upload/qualifiedpics/day_171217/thumb/201712171616179069_700_467.jpg","17365560614",200,"成都市,青羊区",
                "无限网络,电梯", "卫浴用品,热水","2020-1-2,2020-2-2","这是测试房屋",0,0);*/
/*        House(id=null, title=我的房屋2, rentalType=complete, bedNumber=1, peopleNumber=1, houseType=4, score=5, commentsNumber=0, img=,http://kcxbucket.oss-cn-shenzhen.aliyuncs.com/house1607862826000.jpg,,,, headImg=https://pic.tujia.com/upload/festatic/app/tujia_useravatar.png, username=17365560614, housePrice=400, cityAndRegion=,成都市,青羊区,,成都市青羊区青羊工业园区,
        matchingList=无线网络, houseList=酒店式公寓, chooseDate=2020-12-13,2020-12-14, describe=我的, isReserve=0, state=0*/
        House house=new House(1,"我的房屋2","complete", 1,2,4,BigDecimal.valueOf(5),
                0,",http://kcxbucket.oss-cn-shenzhen.aliyuncs.com/house1607862826000.jpg,,,,",
                "https://pic.tujia.com/upload/landlordunit/day_180416/thumb/201804161724089457_700_467.jpg,",
                "17365560614",400,",成都市,青羊区,,成都市青羊区青羊工业园区",
                "无限网络,电梯", "卫浴用品,热水","2020-1-2,2020-2-2","我的",0);
        System.out.println(houseMapper.addHouse(house));
    }

    @Test
    void updateHouse(){
        House house=new House(19,"【谷物烘焙室】 三里屯大使馆news","single", 5,5,5,BigDecimal.valueOf(5.0),
                100,"https://pic.tujia.com/upload/landlordunit","https://pic.tujia.com/upload/landlordunit/day_180416/thumb/201804161724089457_700_467.jpg," +
                "https://pic.tujia.com/upload/qualifiedpics/day_171217/thumb/201712171616179069_700_467.jpg","17365560614",800,"成都市,青羊区,月华路",
                "无限网络,电梯，卫浴用品,热水淋浴", "酒店式公寓","2021-1-2,2021-2-2","这是测试房屋new",1);
        System.out.println(houseMapper.updateHouse(house));
    }

    @Test
    void deleteHouse(){
        System.out.println(houseMapper.deleteHouse(19));
    }

}
