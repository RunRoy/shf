package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.HouseUser;
import com.atguigu.service.HouseUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author lystart
 * @create 2023-05-06 9:53
 */
@RequestMapping("/houseUser")
@Controller
public class HouseUserController {
    //houseUser/create?houseId=[[${house.id}]]'

    @Reference
    private HouseUserService houseUserService;

    private final static String LIST_ACTION = "redirect:/house/";

    private final static String PAGE_CREATE = "houseUser/create";
    private final static String PAGE_EDIT = "houseUser/edit";
    private final static String PAGE_SUCCESS = "common/successPage";


    /**
     * 进入新增页面
     * @param map
     * @param houseId
     * @return
     */
    @RequestMapping("/create")
    public String create(Map map, @RequestParam Long houseId){
        map.put("houseId",houseId);
        return PAGE_CREATE;
    }

    /**
     * 提交新增表单
     * @param houseUser
     * @return
     */
    @RequestMapping("/save")
    public  String save(HouseUser houseUser){
        houseUserService.insert(houseUser);
       return PAGE_SUCCESS;
    }

    /**
     * 进入修改页面回显数据
     */
    @RequestMapping("/edit/{id}")
    public  String edit(ModelMap map, @PathVariable Long id){
        HouseUser houseUser = houseUserService.getById(id);
        map.put("houseUser",houseUser);
        return PAGE_EDIT;
    }

    /**
     * 提交修改表单
     */
    @RequestMapping("/update")
    public String update(HouseUser houseUser){
        houseUserService.update(houseUser);
        return PAGE_SUCCESS;
    }

    ///houseUser/delete/[[${house.id}]]/'+id
    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable Long houseId,@PathVariable Long id){
        houseUserService.delete(id);
       return LIST_ACTION + houseId;
    }
}
