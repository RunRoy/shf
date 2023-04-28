package com.atguigu.service.impl;

import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.AdminDao;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author lystart
 * @create 2023-04-28 9:14
 */
@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {

    @Resource
    AdminDao adminDao;

    @Override
    protected BaseDao getEntityDao() {
        return adminDao;
    }
}
