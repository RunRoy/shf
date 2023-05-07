package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Dict;
import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dict")
public class DictController extends BaseController {
    @Reference
    private DictService dictService;

    /**
     * 根据编码获取子节点数据列表
     * @param
     * @return
     */
    @RequestMapping(value = "findListByDictCode/{dictCode}")
    public Result<List<Dict>> findListByDictCode(@PathVariable String dictCode) {
        List<Dict> list = dictService.findListByDictCode(dictCode);
        return Result.ok(list);
    }

    @RequestMapping(value = "findListByParentId/{parentId}")
    public Result<List<Dict>> findListByParentId(@PathVariable Long parentId) {
        List<Dict> list = dictService.findListByParentId(parentId);
        return Result.ok(list);
    }

}
