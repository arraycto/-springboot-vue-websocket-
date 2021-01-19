package com.kcx.mapper;

import com.kcx.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface UserMapper {
    /*查询所有用户*/
    List<User> queryUserList();
    /*查询是否存在用户*/
    User queryIsExitUser(@Param("username")String username,@Param("password") String password,@Param("code") String code);
    /*添加用户*/
    Integer addUser(User user);
    /*修改用户*/
    Integer updateUser(User user);
    /*删除用户*/
    Integer deleteUser(@Param("username")String username);
}
