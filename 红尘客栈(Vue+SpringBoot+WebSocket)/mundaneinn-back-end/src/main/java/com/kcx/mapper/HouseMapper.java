package com.kcx.mapper;

import com.kcx.pojo.House;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HouseMapper {
    /*得到房屋信息*/
    List<House> queryHouseList(@Param("cityName")String cityName,@Param("peopleNumber") Integer peopleNumber
            ,@Param("bedNumber") Integer bedNumber,@Param("houseType") Integer houseType,@Param("housePrice") Integer housePrice,
             @Param("rentalType") String rentalType,@Param("chooseDate") String chooseDate, @Param("pageSize") Integer pageSize,@Param("page") Integer page);
    /*得到房屋的数量*/
    Integer queryHouseTotal(@Param("cityName")String cityName,@Param("peopleNumber") Integer peopleNumber
            ,@Param("bedNumber") Integer bedNumber,@Param("houseType") Integer houseType,@Param("housePrice") Integer housePrice,
             @Param("rentalType") String rentalType,@Param("chooseDate") String chooseDate);
    /*根据标题和房东用户名或根据房东用户名得到房屋*/
    List<House> queryHouse(@Param("title")String title,@Param("username")String username);
    /*管理员查询得到房屋*/
    List<House> queryHouseByAdmin(@Param("state")Integer state,@Param("cityName")String cityName,@Param("username")String username,
                                  @Param("title")String title, @Param("pageSize") Integer pageSize,@Param("page") Integer page);
    /*管理员查询得到房屋数量*/
    Integer queryHouseTotalByAdmin(@Param("state")Integer state,@Param("cityName")String cityName,@Param("username")String username,
                                   @Param("title")String title);
    /*添加房屋*/
    Integer addHouse(House house);
    /*修改房屋*/
    Integer updateHouse(House house);
    /*删除房屋*/
    Integer deleteHouse(@Param("id")Integer id);
}
