package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.House;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author lystart
 * @create 2023-05-05 14:59
 */
public interface HouseDao extends BaseDao<House> {
    Page<HouseVo> findListPage(@Param("vo") HouseQueryVo houseQueryVo);
}
