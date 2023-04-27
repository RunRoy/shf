package com.atguigu.base;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author lystart
 * @create 2023-04-27 21:05
 */
public interface BaseService<T> {

    /**
     * 查询所有数据
     */
    List<T> findAll();

    /**
     * 添加
     * @param
     */
    void insert(T t);

    /**
     * 根据id查询
     * @param id
     */
    T getById(Serializable id);

    Integer update(T t);

    void delete(Serializable id);

    PageInfo<T> findPage(Map<String, Object> filters);
}
