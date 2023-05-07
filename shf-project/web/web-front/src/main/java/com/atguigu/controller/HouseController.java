package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Community;
import com.atguigu.entity.House;
import com.atguigu.entity.HouseBroker;
import com.atguigu.entity.HouseImage;
import com.atguigu.result.Result;
import com.atguigu.service.CommunityService;
import com.atguigu.service.HouseBrokerService;
import com.atguigu.service.HouseImageService;
import com.atguigu.service.HouseService;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value="/house")
public class HouseController {

    @Reference
    private HouseService houseService;

    @Reference
    private CommunityService communityService;

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private HouseImageService houseImageService;


    /**
     * 房源列表
     *
     * @return
     */
//获取房源
    @RequestMapping("/list/{pageNum}/{pageSize}")
    public Result findListPage(@RequestBody HouseQueryVo houseQueryVo ,
                               @PathVariable("pageNum") Integer pageNum,
                               @PathVariable("pageSize") Integer pageSize){
        //调用HouseService中前端展示房源数据的方法
        PageInfo<HouseVo> pageInfo = houseService.findListPage(pageNum,pageSize,houseQueryVo);
        return Result.ok(pageInfo);
    }

    //查看房源详情
    @RequestMapping("/info/{houseId}")
    public Result info(@PathVariable("houseId") Long houseId , HttpServletRequest request){
        //调用HouseService中根据id查询房源的方法
        House house = houseService.getById(houseId);
        //根据房源中小区的id获取所在的小区
        Community community = communityService.getById(house.getCommunityId());
        //获取该房源的经纪人
        List<HouseBroker> houseBrokers = houseBrokerService.findListByHouseId(houseId);
        //获取房源图片
        List<HouseImage> houseImages = houseImageService.findList(houseId, 1);
        Boolean isFollowd = false;
        //创建一个Map
        Map map = new HashMap<>();
        map.put("house",house);
        map.put("community",community);
        map.put("houseBrokerList",houseBrokers);
        map.put("houseImage1List",houseImages);

        //是否关注了该房源
        map.put("isFollow",isFollowd);
        return Result.ok(map);
    }

}