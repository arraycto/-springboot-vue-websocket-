package com.kcx.controller;

import com.kcx.mapper.UserMapper;
import com.kcx.pojo.User;
import com.kcx.utils.CodeUtils;
import com.kcx.utils.SendSms;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserMapper userMapper;

    /*发送短信*/
    @GetMapping("/sendMsg/{username}")
    public String sendMsg(@PathVariable("username") String username){
        String code=CodeUtils.getCode();
        //发送短信，失败直接返回
        try{
          if(!SendSms.sendSms(username,code).equals("OK")){
              return "error";
          }
        }
        catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        User user= userMapper.queryIsExitUser(username,null,null);
        //如果不存在，直接添加用户
        if(user==null){
                 user=new User(username,"",code,"","https://pic.tujia.com/upload/festatic/app/tujia_useravatar.png","",
                        "",null,"","true","false","normal",0);
                userMapper.addUser(user);
        }
        //如果存在，修改用户验证码
        else{
            user.setCode(code);
            userMapper.updateUser(user);
        }
        return "success";
    }
    /*登录或得到用户信息*/
    @GetMapping("/login/{username}/{code}/{password}")
    public User login(@PathVariable("username") String username, @PathVariable("code")String code, @PathVariable("password") String password){
        if(code.equals("null")&&!password.equals("null")){//密码登录
           return userMapper.queryIsExitUser(username,DigestUtils.md5Hex(password),null);
        }
        if(password.equals("null")&&!code.equals("null")){//验证码登录
            return userMapper.queryIsExitUser(username,null,code);
        }
        if(password.equals("null")&&code.equals("null")){//得到用户信息
            return userMapper.queryIsExitUser(username,null,null);
        }
        return null;
    }

    /*修改用户*/
    @PutMapping("/updateUser")
    public void login(@RequestBody User user){
        //如果更改了密码，则将更改后的密码加密存入
        if (userMapper.queryIsExitUser(user.getUsername(), user.getPassword(), null) == null) {
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        }
        //如果没有更改密码，直接修改数据
        userMapper.updateUser(user);

    }
}
