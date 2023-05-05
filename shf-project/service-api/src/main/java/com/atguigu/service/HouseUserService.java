package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.HouseUser;

import java.util.List;

/**
 * @author lystart
 * @create 2023-05-05 19:48
 */
public interface HouseUserService extends BaseService<HouseUser> {
    List<HouseUser> findListByHouseId(Long id);
}
