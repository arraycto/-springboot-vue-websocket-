package com.kcx.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kcx.mapper.HouseMapper;
import com.kcx.pojo.House;
import com.kcx.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
@SuppressWarnings("all")//取消所有的单词错误警告
@RestController
@RequestMapping("/house")
public class HouseController {
    @Autowired
    HouseMapper houseMapper;
    JSONObject jsonObject;
    /*得到房屋数量*/
    @PostMapping("/getHouseTotal")
    public Integer getHouseTotal(@RequestBody Object obj){
        jsonObject= (JSONObject) JSON.toJSON(obj);
        String city=jsonObject.getString("currentCityName");
        String peopleNumber=jsonObject.getString("peopleNumber");
        String bedNumber=jsonObject.getString("bedNumber");
        String houseType=jsonObject.getString("houseType");
        String housePrice=jsonObject.getString("housePrice");
        String rentalType=jsonObject.getString("rentalType");
        String chooseDate=jsonObject.getString("chooseDate");
        return  houseMapper.queryHouseTotal(city,peopleNumber==""?null:Integer.valueOf(peopleNumber),
                bedNumber==""?null:Integer.valueOf(bedNumber), houseType==""?null:Integer.valueOf(houseType),
                housePrice==""?null:Integer.valueOf(housePrice),rentalType==""?null:rentalType,chooseDate==""?null:chooseDate);
    }
    /*根据筛选条件得到房屋列表*/
    @PostMapping("/getHouseList/{page}/{pageSize}")
    public List<House> getHouseList(@RequestBody Object obj,@PathVariable("page") Integer page,@PathVariable("pageSize") Integer pageSize){
        jsonObject = (JSONObject) JSON.toJSON(obj);
        String city=jsonObject.getString("currentCityName");
        String peopleNumber=jsonObject.getString("peopleNumber");
        String bedNumber=jsonObject.getString("bedNumber");
        String houseType=jsonObject.getString("houseType");
        String housePrice=jsonObject.getString("housePrice");
        String rentalType=jsonObject.getString("rentalType");
        String chooseDate=jsonObject.getString("chooseDate");
        return  houseMapper.queryHouseList(city,peopleNumber==""?null:Integer.valueOf(peopleNumber),
                bedNumber==""?null:Integer.valueOf(bedNumber), houseType==""?null:Integer.valueOf(houseType),
                housePrice==""?null:Integer.valueOf(housePrice),rentalType==""?null:rentalType,
                chooseDate==""?null:chooseDate,pageSize,(page-1)*pageSize);
    }
    /*得到单个房屋或某个用户的房屋*/
    @GetMapping("/getHouse/{username}/{title}")
    public List<House> getHouse(@PathVariable("username")String username,@PathVariable("title")String title){
        if(title.equals("null")) title=null;
        return houseMapper.queryHouse(title,username);
    }
    /*管理员得到房屋*/
    @GetMapping("/getHouseByAdmin/{state}/{cityName}/{username}/{title}/{page}/{pageSize}")
    public List<House> getHouseByAdmin(@PathVariable("state")String state,@PathVariable("cityName")String cityName,
                                       @PathVariable("username")String username,@PathVariable("title")String title,
                                       @PathVariable("page") Integer page,@PathVariable("pageSize") Integer pageSize){
        Integer thisState=state.equals("null")?null:Integer.valueOf(state);
        String thisCityName=cityName.equals("null")?null:cityName;
        String thisUsername=username.equals("null")?null:username;
        String thisTitle=title.equals("null")?null:title;
        return houseMapper.queryHouseByAdmin(thisState,thisCityName,thisUsername,thisTitle,pageSize,(page-1)*pageSize);
    }
    /*管理员得到房屋数量*/
    @GetMapping("/getHouseTotalByAdmin/{state}/{cityName}/{username}/{title}")
    public Integer getHouseTotalByAdmin(@PathVariable("state")String state,@PathVariable("cityName")String cityName,
                                        @PathVariable("username")String username,@PathVariable("title")String title){
        Integer thisState=state.equals("null")?null:Integer.valueOf(state);
        String thisCityName=cityName.equals("null")?null:cityName;
        String thisUsername=username.equals("null")?null:username;
        String thisTitle=title.equals("null")?null:title;
        return  houseMapper.queryHouseTotalByAdmin(thisState,thisCityName,thisUsername,thisTitle);
    }
    /*添加房屋*/
    @PostMapping("/addHouse")
    public String addHouse(@RequestBody House house){
        if(houseMapper.queryHouse(house.getTitle(),house.getUsername()).size()==0&&houseMapper.addHouse(house)==1){
            return "success";
        }
        return "error";
    }

    /*修改房屋*/
    @PutMapping("/updateHouse")
    public String updateHouse(@RequestBody House house){
        if(houseMapper.updateHouse(house)==1)
            return "success";
        return "error";
    }

}
