package com.kcx.controller;

import com.kcx.mapper.CollectMapper;
import com.kcx.pojo.Collect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    CollectMapper collectMapper;
    /*得到用户收藏*/
    @GetMapping("/getCollect/{username}")
    public List<Collect> getCollect(@PathVariable("username")String username){
        return  collectMapper.queryUserCollections(username);
    }
    /*添加用户收藏*/
    @PostMapping("/addCollect")
    public void addCollect(@RequestBody Collect collect){
        collectMapper.addCollection(collect);
    }
    /*删除用户收藏*/
    @DeleteMapping("/deleteCollect/{id}")
    public void deleteCollect(@PathVariable("id") Integer id){
        collectMapper.deleteCollection(id);
    }
}
