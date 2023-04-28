package com.atguigu.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.AdminDao;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * @author lystart
 * @create 2023-04-28 9:14
 */
@Service(interfaceClass = AdminService.class)
@Transactional
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {

    @Resource
    AdminDao adminDao;

    @Override
    protected BaseDao getEntityDao() {
        return adminDao;
    }
}
