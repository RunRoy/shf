package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.DictDao;
import com.atguigu.dao.HouseDao;
import com.atguigu.entity.House;
import com.atguigu.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * @author lystart
 * @create 2023-05-05 14:58
 */
@Service(interfaceClass = HouseService.class)
public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService {


    @Autowired
    DictDao dictDao;

    @Autowired
    protected HouseDao houseDao;

    @Override
    protected BaseDao<House> getEntityDao() {
        return houseDao;
    }

    @Override
    public House getById(Serializable id) {
        House house = houseDao.getById(id);
        house.setHouseTypeName(dictDao.getNameById(house.getHouseTypeId()));
        house.setFloorName(dictDao.getNameById(house.getFloorId()));
        house.setBuildStructureName(dictDao.getNameById(house.getBuildStructureId()));
        house.setDirectionName(dictDao.getNameById(house.getDirectionId()));
        house.setDecorationName(dictDao.getNameById(house.getDecorationId()));
        house.setHouseUseName(dictDao.getNameById(house.getHouseUseId()));
        return house;
    }




    @Override
    public void publish(Long id, Integer status) {

    }
}
