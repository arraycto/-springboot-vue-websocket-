package com.kcx;

import com.kcx.mapper.OrderMapper;
import com.kcx.pojo.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderTest {
    @Autowired
    OrderMapper orderMapper;
    @Test
    public void queryOrderList(){
        //System.out.println(orderMapper.queryOrderList("13528885555",null));
        System.out.println(orderMapper.queryOrderList(null,"17365560614"));
    }
    @Test
    public void queryOrder(){
        Order order =new Order(null,"2020-12-1,2020-12-3",221,"天空一号",
                "13528885555","173655606144",0);
        System.out.println(orderMapper.queryOrder(order));
    }
    @Test
    public void queryOrderByTitle(){
        System.out.println(orderMapper.queryOrderByTitle("17365560614","成都青羊苏坡立交豪华公寓"));
    }
    @Test
    public void addOrder(){
        Order order =new Order(null,"2020-12-28,2020-12-29",400,"测试订单",
                "1111111","2222222",0);
        System.out.println(orderMapper.addOrder(order));
    }
    @Test
    public void updateOrder(){
        Order order =new Order(3,"2020-12-28,2020-12-29",400,"测试订单new",
                "11111112","22222221",0);
        System.out.println(orderMapper.updateOrder(order));
    }

    @Test
    public void deleteOrder(){
        System.out.println(orderMapper.deleteOrder(3));
    }
}
