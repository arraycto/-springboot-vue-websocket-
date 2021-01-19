package com.kcx.mapper;

import com.kcx.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderMapper {
    /*得到某个用户或商家的订单*/
    List<Order> queryOrderList(@Param("username") String username,@Param("customerId")String customerId);
    /*查询某个房屋某一时段是否已存在订单*/
    Order queryOrder(Order order);
    /*查询某个用户的某个房屋正在进行的订单*/
    List<Order> queryOrderByTitle(@Param("username") String username,@Param("title")String title);
    /*添加订单*/
    Integer addOrder(Order order);
    /*修改用户订单*/
    Integer updateOrder(Order order);
    /*删除订单*/
    Integer deleteOrder(@Param("id") Integer id);
}
