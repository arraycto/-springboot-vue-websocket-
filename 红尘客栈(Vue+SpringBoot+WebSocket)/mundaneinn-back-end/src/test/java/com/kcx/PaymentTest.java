package com.kcx;

import com.kcx.mapper.PaymentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PaymentTest {
    @Autowired
    PaymentMapper paymentMapper;
    @Test
    public void queryPaymentByUser(){
        //System.out.println(orderMapper.queryOrderList("13528885555",null));
        System.out.println(paymentMapper.queryPaymentByUser("17365560614"));
    }
    @Test
    void deletePaymentById(){
        System.out.println(paymentMapper.deletePaymentById(3));
    }
}
