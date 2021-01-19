package com.kcx.controller;

import com.kcx.mapper.PaymentMapper;
import com.kcx.pojo.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    PaymentMapper paymentMapper;
    /*得到支付记录*/
    @GetMapping("/getPaymentByUser/{username}")
    public List<Payment> getPaymentByUser(@PathVariable("username")String username){
        return paymentMapper.queryPaymentByUser(username);
    }
    /*删除支付记录*/
    @DeleteMapping("/deletePaymentById/{id}")
    public void deletePaymentById(@PathVariable("id")Integer id){
         paymentMapper.deletePaymentById(id);
    }
}
