package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.entity.HouseBroker;
import com.atguigu.service.AdminService;
import com.atguigu.service.HouseBrokerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author lystart
 * @create 2023-05-05 23:02
 */
@RequestMapping("/houseBroker")
@Controller
public class HouseBrokerController {

    @Reference
   protected HouseBrokerService houseBrokerService;

    @Reference
   protected AdminService adminService;

    private final static String LIST_ACTION = "redirect:/house/";
    private final static String PAGE_CREATE = "houseBroker/create";
    private final static String PAGE_EDIT = "houseBroker/edit";
    private final static String PAGE_SUCCESS = "common/successPage";

    @RequestMapping("/create")
    public  String create(ModelMap map, @RequestParam Long houseId){
        List<Admin> adminList = adminService.findAll();
        map.addAttribute("adminList",adminList);
        map.addAttribute("houseId",houseId);
        return PAGE_CREATE;
    }

    @RequestMapping("/save")
    public String save(HouseBroker houseBroker){
        //根据HouseBroker对象中经纪人的id获取经纪人对象
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        //将经纪人的名字，头像地址设置到houseBroker对象中
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());

        houseBrokerService.insert(houseBroker);
        return PAGE_SUCCESS;
    }

    /**
     * 进入修改窗口回显数据
     * @param id
     * @param map
     * @return
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id,ModelMap map){
        HouseBroker houseBroker = houseBrokerService.getById(id);

        List<Admin> adminList = adminService.findAll();

        map.put("houseBroker",houseBroker);
        map.put("adminList",adminList);
        return PAGE_EDIT;
    }

    /**
     * 提交修改表单
     * @param houseBroker
     * @return
     */
    @RequestMapping("/update")
    public String update(HouseBroker houseBroker){
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBrokerService.update(houseBroker);
        return LIST_ACTION;
    }

    /**
     * 删除
     */
    @RequestMapping("/delete/{houseId}/{id}")
   public String delete(@PathVariable("houseId") Long houseId,@PathVariable Long id){
        houseBrokerService.delete(id);
        return "redirect:/house/"+ houseId;
   }
}
