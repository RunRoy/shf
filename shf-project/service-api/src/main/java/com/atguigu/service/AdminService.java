package com.atguigu.service;


import com.atguigu.base.BaseService;
import com.atguigu.entity.Admin;

import java.util.List;
import java.util.Map;

/**
 * @author lystart
 * @create 2023-04-28 9:13
 */
public interface AdminService extends BaseService<Admin> {


    Map<String, Object> findRoleByAdminId(Long adminId);}
