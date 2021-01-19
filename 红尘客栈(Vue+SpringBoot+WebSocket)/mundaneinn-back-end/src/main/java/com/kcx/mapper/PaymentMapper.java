package com.kcx.mapper;

import com.kcx.pojo.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PaymentMapper {
    /*根据房东名得到付款记录*/
    List<Payment> queryPaymentByUser(@Param("username")String username);
    /*根据id删除付款记录*/
    Integer deletePaymentById(@Param("id")Integer id);
}
