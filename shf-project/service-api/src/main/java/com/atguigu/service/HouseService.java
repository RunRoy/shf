package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.House;

/**
 * @author lystart
 * @create 2023-05-05 14:53
 */
public interface HouseService extends BaseService<House> {
    void publish(Long id, Integer status);
}
