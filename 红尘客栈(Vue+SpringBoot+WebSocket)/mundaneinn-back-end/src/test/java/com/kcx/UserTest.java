package com.kcx;
import com.kcx.mapper.UserMapper;
import com.kcx.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {
    @Autowired
    UserMapper userMapper;
    @Test
    void queryUserList(){
        System.out.println(userMapper.queryUserList());
    }
    @Test
    void queryIsExitUser(){
        System.out.println(userMapper.queryIsExitUser("17365560614",null,null));
    }
    @Test
    void addUser(){
        try{
/*            User user=new User("100001","123","","管理员","","",
                    "0",20,"成都","false","false","admin");*/
            User  user=new User("17365560666","","zwcx","","https://pic.tujia.com/upload/festatic/app/tujia_useravatar.png","",
                    "",null,"","false","false","normal",0);
            System.out.println(userMapper.addUser(user));//添加成功返回1
        }
        catch (Exception e){
            System.out.println(0);//失败返回0
        }
    }

    @Test
    void updateUser(){
        User user=new User("17365560666","123456","","管理员","","",
                "0",20,"成都","false","false","admin",0);
        System.out.println(userMapper.updateUser(user));//成功返回1
    }

    @Test
    void deleteUser(){
        System.out.println(userMapper.deleteUser("17365560666"));//成功返回1
    }
}
