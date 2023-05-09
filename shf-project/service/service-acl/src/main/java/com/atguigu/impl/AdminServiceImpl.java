package com.atguigu.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.AdminDao;
import com.atguigu.dao.AdminRoledao;
import com.atguigu.dao.RoleDao;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Role;
import com.atguigu.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author lystart
 * @create 2023-04-28 9:14
 */
@Service(interfaceClass = AdminService.class)
@Transactional
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {


    @Autowired
    private RoleDao roleDao;

    @Resource
    protected AdminDao adminDao;

    @Autowired
    protected AdminRoledao adminRoledao;


    @Override
    protected BaseDao getEntityDao() {
        return adminDao;
    }


    @Override
    public Map<String, Object> findRoleByAdminId(Long adminId) {
        // 获取所有的角色
        List<Role> allRoleList = roleDao.findAll();
        // 根据用户id，查询用户已经拥有的RoleId
        List<Long> existRoleList = adminRoledao.findRoleIdByAdminId(adminId);
        // 对角色进行分类
        // 所有的角色
        List<Role> noAssginRoleList = new ArrayList<>();
        // 已经分配的角色
        List<Role> assginRoleList = new ArrayList<>();
        for (Role role : allRoleList) {
            // 判断existRoleList里面是否有allRoleList里面的id
            if (existRoleList.contains(role.getId())) {
                // 已经被选择的角色
                assginRoleList.add(role);
            } else {
                // 没有被选中的角色
                noAssginRoleList.add(role);
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("noAssginRoleList", noAssginRoleList);
        map.put("assginRoleList", assginRoleList);

        return map;
    }
}
