package com.atguigu.base;

import javax.management.relation.Role;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author lystart
 * @create 2023-04-27 20:55
 */
public interface BaseDao<T> {

    List<T> findAll();

    void insert(T t);
    T getById(Serializable id);

    Integer update(T t);

    void delete(Serializable id);

    List<T> findPage(Map<String, Object> filters);

}
