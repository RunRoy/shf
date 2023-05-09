package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Permission;

import java.util.List;
import java.util.Map;

/**
 * @author lystart
 * @create 2023-05-09 21:01
 */
public interface PermissionService extends BaseService<Permission> {
    List<Map<String, Object>> findPermissionByRoleId(Long roleId);

    void insertRoleAndPermission(Long[] permissionIds, Long roleId);

    List<Permission> findMenuPermissionByAdminId(Long adminId);

    //菜单
    List<Permission> findAllMenu();


}
