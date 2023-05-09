package com.atguigu.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.AdminRoledao;
import com.atguigu.dao.RoleDao;
import com.atguigu.entity.AdminRole;
import com.atguigu.entity.Role;
import com.atguigu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;


/**
 * @author lystart
 * @create 2023-04-26 16:44
 */
@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Resource
  private RoleDao roleDao;

    @Autowired
    AdminRoledao adminRoleDao;

    @Override
    protected BaseDao<Role> getEntityDao() {
        return roleDao;
    }


    @Override
    public void insertAdminAndRole(Long adminId, Long[] roleIds) {
        // 先把中间表用户已经拥有的角色给删除
        adminRoleDao.deleteByAdminId(adminId);


        for (Long roleId : roleIds) {
            // 插入数据之前，先判断当前角色id是否有值
            //["编辑","","浏览"]
            if (StringUtils.isEmpty(roleId))
                continue;
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(adminId);
            adminRole.setRoleId(roleId);
            adminRoleDao.insert(adminRole);
        }
    }
}
