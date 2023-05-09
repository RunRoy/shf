package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.AdminRole;

import java.util.List;

/**
 * @author lystart
 * @create 2023-05-09 18:44
 */
public interface AdminRoledao extends BaseDao<AdminRole> {

    List<Long> findRoleIdByAdminId(Long adminId);

    void deleteByAdminId(Long adminId);
}
