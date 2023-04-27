package com.atguigu.controller;

import com.atguigu.entity.Role;
import com.atguigu.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lystart
 * @create 2023-04-26 16:38
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService service;
    private final static String PAGE_INDEX = "role/index";
    private final static String PAGE_CREATE = "role/create";
    private final static String PAGE_EDIT = "role/edit";
    private final static String PAGE_ROLE = "redirect:/role";


    public Map<String, Object> getFilters(HttpServletRequest request) {
        // 获取 pageName,pageSize
        Enumeration parameterNames = request.getParameterNames();
        Map<String, Object> filters = new HashMap<>();
        // 判空且有下一个元素
        while (parameterNames != null && parameterNames.hasMoreElements()) {
            String keys = (String) parameterNames.nextElement();
            String[] values = request.getParameterValues(keys);
            if (keys != null && values.length != 0) {
                if (values.length > 1) {
                    filters.put(keys, values);
                } else {
                    filters.put(keys, values[0]);
                }
            }
        }
        if (!filters.containsKey("pageNum")) {
            filters.put("pageNum", 1);
        }
        if (!filters.containsKey("pageSize")) {
            filters.put("pageSize", 2);
        }
        return filters;
    }


//    /**
//     * 查列表
//     *
//     * @param model
//     * @return
//     */
//    @RequestMapping
//    public String index(ModelMap model) {
//        List<Role> roleList = service.findAll();
//        model.addAttribute("roleList", roleList);
//        return PAGE_INDEX;
//    }

    /**
     * 查列表
     *
     * @param model
     * @return
     */
    @RequestMapping
    public String index(ModelMap model,HttpServletRequest request) {
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
