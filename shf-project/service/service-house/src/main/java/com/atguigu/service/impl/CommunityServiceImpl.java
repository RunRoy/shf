package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.CommunityDao;
import com.atguigu.dao.DictDao;
import com.atguigu.entity.Community;
import com.atguigu.entity.Dict;
import com.atguigu.service.CommunityService;
import com.atguigu.service.DictService;
import com.atguigu.util.CastUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author lystart
 * @create 2023-05-04 18:27
 */
@Service(interfaceClass = CommunityService.class)
public class CommunityServiceImpl extends BaseServiceImpl<Community> implements CommunityService{

    @Autowired
    private DictDao dictDao;

    @Autowired
    private CommunityDao communityDao;

    @Override
    protected BaseDao<Community> getEntityDao() {
        return communityDao;
    }

    @Override
    public Community getById(Serializable id) {
        Community community = communityDao.getById(id);
        if (community == null) {
            return null;
        }
        String areaName = dictDao.getNameById(community.getAreaId());
        String plateName = dictDao.getNameById(community.getPlateId());
        community.setAreaName(areaName);
        community.setPlateName(plateName);
        return community;
    }



    @Override
    public PageInfo<Community> findPage(Map<String, Object> filters) {
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 2);
        PageHelper.startPage(pageNum,pageSize);
        List<Community> list = communityDao.findPage(filters);
        for (Community community : list) {
            Long areaId = community.getAreaId();
            String areaName =  dictDao.getNameById(areaId);
            Long plateId = community.getPlateId();
            String plateName = dictDao.getNameById(plateId);
            community.setAreaName(areaName);
            community.setPlateName(plateName);
        }
        return new PageInfo<Community>(list,10);
    }
}
