package com.atguigu.service;



import com.atguigu.base.BaseService;
import com.atguigu.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * @author lystart
 * @create 2023-04-26 16:42
 */

public interface RoleService extends BaseService<Role> {

    void insertAdminAndRole(Long adminId, Long[] roleIds);
}
