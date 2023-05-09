package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.Permission;

import java.util.List;

/**
 * @author lystart
 * @create 2023-05-09 21:04
 */
public interface PermissionDao extends BaseDao<Permission> {
    List<Permission> findListByAdminId(Long adminId);
}
