package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.*;
import com.atguigu.service.*;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lystart
 * @create 2023-05-05 11:48
 */
@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {

    @Reference
    HouseImageService houseImageService;

    @Reference
    HouseUserService houseUserService;

    @Reference
    HouseBrokerService houseBrokerService;

    @Reference
    protected CommunityService communityService;

    @Reference
    protected DictService dictService;

    @Reference
    protected HouseService houseService;

    private final static String LIST_ACTION = "redirect:/house";

    private final static String PAGE_INDEX = "house/index";
    private final static String PAGE_SHOW = "house/show";
    private final static String PAGE_CREATE = "house/create";
    private final static String PAGE_EDIT = "house/edit";
    private final static String PAGE_SUCCESS = "common/successPage";

    /**
     * 列表
     * @param request
     * @param map
     * @return
     */
    @RequestMapping
    public String index(HttpServletRequest request, ModelMap map) {
        Map<String, Object> filters = getFilters(request);
        PageInfo<House> page = houseService.findPage(filters);
        //分页
        map.addAttribute("page", page);
        map.addAttribute("filters", filters);
        // 全部小区
        map.addAttribute("communityList", communityService.findAll());
        // 户型
        map.addAttribute("houseTypeList", dictService.findListByDictCode("houseType"));
        // 楼层
        map.addAttribute("floorList", dictService.findListByDictCode("floor"));
        // 建筑结构
        map.addAttribute("buildStructureList", dictService.findListByDictCode("buildStructure"));
        // 朝向
        map.addAttribute("directionList", dictService.findListByDictCode("direction"));
        // 装修情况
        map.addAttribute("decorationList", dictService.findListByDictCode("decoration"));
        // 房屋用途
        map.addAttribute("houseUseList", dictService.findListByDictCode("houseUse"));

        return PAGE_INDEX;
    }

    /**
     * 跳转新增页面
     */
    @RequestMapping("/create")
    public String create(ModelMap map){
        map.addAttribute("communityList", communityService.findAll());
        map.addAttribute("houseTypeList", dictService.findListByDictCode("houseType"));
        map.addAttribute("floorList", dictService.findListByDictCode("floor"));
        map.addAttribute("buildStructureList", dictService.findListByDictCode("buildStructure"));
        map.addAttribute("directionList", dictService.findListByDictCode("direction"));
        map.addAttribute("decorationList", dictService.findListByDictCode("decoration"));
        map.addAttribute("houseUseList", dictService.findListByDictCode("houseUse"));
        return PAGE_CREATE;
    }

    /**
     * 提交新增数据
     */
    @RequestMapping("/save")
    public String save(House house){
        house.setStatus(0) ;
        houseService.insert(house);
        return PAGE_SUCCESS;
    }

    /**
     * 发布
     */
    @RequestMapping("/publish/{id}/{status}")
    public String publish(@PathVariable Long id,@PathVariable Integer status){
        House house = new House();
        house.setId(id);
        house.setStatus(status);
        houseService.update(house);
        return  LIST_ACTION;
    }

    /**
     * 逻辑删除
     */
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        houseService.delete(id);
        return LIST_ACTION;
    }

    /**
     * 跳转修改页面并回显数据
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id,ModelMap map){
        House house = houseService.getById(id);
        map.addAttribute("house",house);
        map.addAttribute("communityList", communityService.findAll());
        map.addAttribute("houseTypeList", dictService.findListByDictCode("houseType"));
        map.addAttribute("floorList", dictService.findListByDictCode("floor"));
        map.addAttribute("buildStructureList", dictService.findListByDictCode("buildStructure"));
        map.addAttribute("directionList", dictService.findListByDictCode("direction"));
        map.addAttribute("decorationList", dictService.findListByDictCode("decoration"));
        map.addAttribute("houseUseList", dictService.findListByDictCode("houseUse"));
        return PAGE_EDIT;
    }

    /**
     * 提交修改表单
     */
    @RequestMapping("/update")
    public String update(House house){
        houseService.update(house);
        return PAGE_SUCCESS;
    }

    /**
     * 详情
     */
    @RequestMapping("/{id}")
    public String show(@PathVariable Long id,ModelMap map){
        House house = houseService.getById(id);
        Community community = communityService.getById(house.getCommunityId());

        List<HouseImage> houseImage1List = houseImageService.findList(id,1);
        List<HouseImage> houseImage2List = houseImageService.findList(id,2);
        List<HouseBroker> houseBrokerList = houseBrokerService.findListByHouseId(id);
        List<HouseUser> houseUserList = houseUserService.findListByHouseId(id);

        map.addAttribute("house",house);
        map.addAttribute("community",community);
        map.addAttribute("houseBrokerList",houseBrokerList);
        map.addAttribute("houseUserList",houseUserList);
        map.addAttribute("houseImage1List",houseImage1List);
        map.addAttribute("houseImage2List",houseImage2List);
        return PAGE_SHOW;
    }
}
