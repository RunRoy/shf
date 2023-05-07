package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Role;
import com.atguigu.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * @author lystart
 * @create 2023-04-26 16:38
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Reference
    private RoleService service;

    private final static String PAGE_INDEX = "role/index";
    private final static String PAGE_CREATE = "role/create";
    private final static String PAGE_EDIT = "role/edit";
    private final static String PAGE_ROLE = "redirect:/role";

    /**
     * 查列表
     *
     * @param model
     * @return
     */
    @RequestMapping
    public String index(ModelMap model, HttpServletRequest request) {
        Map<String, Object> filters = getFilters(request);
        PageInfo<Role> page = service.findPage(filters);
        model.addAttribute("page", page);
        model.addAttribute("filters", filters);
        return "role/index";
    }

    /**
     * 新增
     *
     * @return
     */
    @RequestMapping("/create")
    public String create(ModelMap model) {
        return PAGE_CREATE;
    }

    /**
     * 提交新增表单
     *
     * @param role
     * @return
     */
    @RequestMapping("/save")
    public String save(Role role, ModelMap modelMap) {
        service.insert(role);
        return "common/successPage";
    }

    /**
     * 根据id查询数据
     */
    @RequestMapping("/edit/{id}")
    public String edit(ModelMap modelMap, @PathVariable Long id) {
        Role role = service.getById(id);
        modelMap.addAttribute(role);
        return PAGE_EDIT;
    }

    /**
     * 提交修改表单
     */
    @RequestMapping("/update")
    public String update(Role role) {
        service.update(role);
        return PAGE_ROLE;
    }

    /**
     * 删除
     */
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return PAGE_ROLE;
    }
}