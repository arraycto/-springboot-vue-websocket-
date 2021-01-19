package com.kcx.mapper;

import com.kcx.pojo.Region;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface RegionMapper {
    /*查询地区*/
    List<Region> queryRegionIf(Map map);
}
