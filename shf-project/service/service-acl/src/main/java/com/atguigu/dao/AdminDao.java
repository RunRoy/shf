package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.Admin;

import java.util.List;
import java.util.Map;

/**
 * @author lystart
 * @create 2023-04-28 9:16
 */
public interface AdminDao extends BaseDao<Admin> {


    Admin getByUsername(String username);
}
