package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.House;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.PageInfo;

/**
 * @author lystart
 * @create 2023-05-05 14:53
 */
public interface HouseService extends BaseService<House> {
    void publish(Long id, Integer status);
    PageInfo<HouseVo> findListPage(int pageNum, int pageSize, HouseQueryVo houseQueryVo);
}
