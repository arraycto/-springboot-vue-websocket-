package com.kcx.controller;

import com.kcx.mapper.RegionMapper;
import com.kcx.pojo.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    RegionMapper regionMapper;
    /*得到分类地区*/
    @GetMapping("/getRegion/{cityName}/{type}")
    public List<Region> getRegion(@PathVariable("cityName") String cityName, @PathVariable("type") String type){
        HashMap map=new HashMap();
        map.put("cityName",cityName);
        if(type!="null"){
            map.put("type",type);
        }
        return regionMapper.queryRegionIf(map);
    }
}
