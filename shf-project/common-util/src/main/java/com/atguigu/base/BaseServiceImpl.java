package com.atguigu.base;

import com.atguigu.util.CastUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author lystart
 * @create 2023-04-27 22:46
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    protected abstract BaseDao<T> getEntityDao();

    @Override
    public void insert(T t) {
        getEntityDao().insert(t);
    }

    @Override
    public List<T> findAll() {
        return getEntityDao().findAll();
    }

    @Override
    public T getById(Serializable id) {
        return getEntityDao().getById(id);
    }

    @Override
    public Integer update(T t) {
        return getEntityDao().update(t);
    }

    @Override
    public void delete(Serializable id) {
        getEntityDao().delete(id);
    }

    @Override
    public PageInfo<T> findPage(Map<String, Object> filters) {
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 2);
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(getEntityDao().findPage(filters), 10);
    }
}
