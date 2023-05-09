package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.UserFollow;
import com.atguigu.entity.UserInfo;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author lystart
 * @create 2023-05-08 19:16
 */
public interface UserFollowDao extends BaseDao<UserFollow> {
    Integer isFollowd(@Param("userId") Long userInfoId,@Param("houseId") Long houseId);

    Page<UserFollowVo> findListPage(Long userInfoId);

}
