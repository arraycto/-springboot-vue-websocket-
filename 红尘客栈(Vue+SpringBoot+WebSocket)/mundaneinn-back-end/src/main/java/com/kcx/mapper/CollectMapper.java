package com.kcx.mapper;

import com.kcx.pojo.Collect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface CollectMapper {
    /*得到用户收藏*/
    List<Collect> queryUserCollections(@Param("userId")String userId);

    /*添加用户收藏*/
    Integer addCollection(Collect collect);

    /*删除用户收藏*/
    Integer deleteCollection(@Param("id")Integer id);
}
