package com.atguigu.base;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lystart
 * @create 2023-04-28 8:26
 */
public class BaseController {

    public Map<String, Object> getFilters(HttpServletRequest request) {
        // 获取 pageName,pageSize
        Enumeration parameterNames = request.getParameterNames();
        Map<String, Object> filters = new HashMap<>();
        // 判空且有下一个元素
        while (parameterNames != null && parameterNames.hasMoreElements()) {
            String keys = (String) parameterNames.nextElement();
            String[] values = request.getParameterValues(keys);
            if (keys != null && values.length != 0) {
                if (values.length > 1) {
                    filters.put(keys, values);
                } else {
                    filters.put(keys, values[0]);
                }
            }
        }
        if (!filters.containsKey("pageNum")) {
            filters.put("pageNum", 1);
        }
        if (!filters.containsKey("pageSize")) {
            filters.put("pageSize", 2);
        }
        return filters;
    }
}
