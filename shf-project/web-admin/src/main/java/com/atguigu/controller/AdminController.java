package com.atguigu.controller;

import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Role;
import com.atguigu.service.AdminService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author lystart
 * @create 2023-04-28 9:09
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    private final static String LIST_ACTION = "redirect:/admin";

    private final static String PAGE_INDEX = "admin/index";
    private final static String PAGE_CREATE = "admin/create";
    private final static String PAGE_EDIT = "admin/edit";
    private final static String PAGE_SUCCESS = "common/successPage";

    @Autowired
    AdminService service;

    @RequestMapping
    public String index(ModelMap model, HttpServletRequest request) {
        Map<String, Object> filters = getFilters(request);
        PageInfo<Admin> page = service.findPage(filters);
        model.addAttribute("page", page);
        model.addAttribute("filters", filters);
        return PAGE_INDEX;
    }

    @RequestMapping("/create")
    public String create(ModelMap modelMap){
        return PAGE_CREATE;
    }

     @RequestMapping("/save")
    public String save(Admin admin){
        service.insert(admin);
        return PAGE_SUCCESS;
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, ModelMap modelMap){
        Admin admin = service.getById(id);
        modelMap.addAttribute(admin);
        return PAGE_EDIT;
    }

    @RequestMapping("/update")
    public  String update(Admin admin){
        service.update(admin);
        return PAGE_SUCCESS;
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id,ModelMap modelMap){
        service.delete(id);
        return LIST_ACTION;
    }


}
