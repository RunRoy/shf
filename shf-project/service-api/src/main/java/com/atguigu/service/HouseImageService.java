package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.HouseImage;

import java.util.List;

/**
 * @author lystart
 * @create 2023-05-05 19:42
 */
public interface HouseImageService extends BaseService<HouseImage> {
    List<HouseImage> findList(Long id, int type);

}
