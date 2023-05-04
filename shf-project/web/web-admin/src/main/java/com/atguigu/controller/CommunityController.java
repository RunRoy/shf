package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Community;
import com.atguigu.entity.Dict;
import com.atguigu.service.CommunityService;
import com.atguigu.service.DictService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author lystart
 * @create 2023-05-04 18:12
 */
@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController {

    @Reference
    CommunityService communityService;

    @Reference
    private DictService dictService;

    private final static String LIST_ACTION = "redirect:/community";

    private final static String PAGE_SHOW = "community/show";
    private final static String PAGE_CREATE = "community/create";
    private final static String PAGE_EDIT = "community/edit";
    private final static String PAGE_SUCCESS = "common/successPage";


    private final static String PAGE_INDEX = "community/index";


    @RequestMapping
    public String index(ModelMap map, HttpServletRequest request) {
        Map<String, Object> filters = getFilters(request);
        if (!filters.containsKey("areaId")) {
            filters.put("areaId", "");
        }
        if (!filters.containsKey("plateId")) {
            filters.put("plateId", "");
        }
        List<Dict> areaList = dictService.findListByDictCode("beijing");
        PageInfo<Community> page = communityService.findPage(filters);
        map.addAttribute("areaList", areaList);
        map.addAttribute("page", page);
        map.addAttribute("filters", filters);
        return PAGE_INDEX;
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        communityService.delete(id);
        return LIST_ACTION;
    }

    /**
     * 跳转新增页面
     */
    @RequestMapping("/create")
    public String create(ModelMap map){
        List<Dict> areaList = dictService.findListByDictCode("beijing");
        map.addAttribute("areaList",areaList);
        return PAGE_CREATE;
    }

    /**
     * 新增数据
     * save
     */
    @RequestMapping("/save")
    public String save(Community community){
        communityService.insert(community);
        return PAGE_SUCCESS;
    }
}
