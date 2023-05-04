package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.DictDao;
import com.atguigu.entity.Dict;
import com.atguigu.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lystart
 * @create 2023-05-03 16:43
 */
@Service(interfaceClass = DictService.class)
public class DictServiceImpl implements DictService {

    @Autowired
    private DictDao dictDao;

    @Override
    public List<Map<String, Object>> findZnodes(Long id) {
        //[{ id:2, isParent:true, name:""}]
        // 获取子节点数据
        List<Dict> dictList = dictDao.findListByParentId(id);
        System.out.println(dictList);
        // 构建ztree数据
        List<Map<String, Object>> zNodes = new ArrayList<>();
        for (Dict dict : dictList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", dict.getId());
            map.put("isParent", dictDao.countIsParent(dict.getId()) > 0);
            map.put("name", dict.getName());
            zNodes.add(map);
        }
        System.out.println(zNodes);
        return zNodes;
    }

    @Override
    public List<Dict> findListByParentId(Long parentId) {
        return dictDao.findListByParentId(parentId);
    }

    @Override
    public List<Dict> findListByDictCode(String dictCode) {
        Dict dict = dictDao.getByDictCode(dictCode);
        if (dict == null)
            return null;
        return this.findListByParentId(dict.getId());
    }


}
