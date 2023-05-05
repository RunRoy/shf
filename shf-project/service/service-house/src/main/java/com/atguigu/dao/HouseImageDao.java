package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lystart
 * @create 2023-05-05 19:44
 */
public interface HouseImageDao extends BaseDao<HouseImage> {
    List<HouseImage> findList(@Param("id") Long id,@Param("type") Integer type);

}
