package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * @author lystart
 * @create 2023-05-03 16:46
 */
public interface DictDao extends BaseDao<Dict> {

    List<Dict> findListByParentId(Long parentId);

    Integer countIsParent(Long id);


    String getNameById(Long id);
    Dict getByDictCode(String dictCode);
}
