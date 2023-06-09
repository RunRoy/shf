package com.atguigu.service;

import com.atguigu.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * @author lystart
 * @create 2023-05-03 16:32
 */
public interface DictService {
    List<Map<String,Object>> findZnodes(Long id);

    /**
     * 根据上级id获取子节点数据列表
     * @param parentId
     * @return
     */
    List<Dict> findListByParentId(Long parentId);

    /**
     * 根据编码获取子节点数据列表
     * @param dictCode
     * @return
     */
    List<Dict> findListByDictCode(String dictCode);

    String getNameById(Long houseTypeId);


}


