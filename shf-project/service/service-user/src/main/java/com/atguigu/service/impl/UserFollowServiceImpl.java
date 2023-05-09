package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.UserFollowDao;
import com.atguigu.entity.UserFollow;
import com.atguigu.service.DictService;
import com.atguigu.service.UserFollowService;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author lystart
 * @create 2023-05-08 19:19
 */
@Service(interfaceClass = UserFollowService.class)
public class UserFollowServiceImpl extends BaseServiceImpl<UserFollow> implements UserFollowService {


    @Reference
    private DictService dictService;


    @Autowired
    protected UserFollowDao userFollowDao;

    @Override
    protected BaseDao<UserFollow> getEntityDao() {
        return userFollowDao;
    }

    @Override
    public void follow(Long userId, Long houseId) {
        UserFollow userFollow = new UserFollow();
        userFollow.setUserId(userId);
        userFollow.setHouseId(houseId);
        userFollowDao.insert(userFollow);
    }

    @Override
    public Boolean isFollow(Long userId, Long houseId) {
        Integer count = userFollowDao.isFollowd(userId, houseId);
        if (count.intValue() == 0) {
            return false;
        }
        return true;
    }

    //<div class="guantext">{{ item.buildArea }}平 {{ item.houseTypeName}} {{ item.floorName}} {{ item.directionName}}</div>
    //
    @Override
    public PageInfo<UserFollowVo> findListPage(Integer pageNum, Integer pageSize, Long userInfoId) {
        PageHelper.startPage(pageNum, pageSize);
        //调用userFollowService中的findListPage方法，传入pageNum、pageSize和userId作为参数。
       Page<UserFollowVo> page = userFollowDao.findListPage(userInfoId);
        List<UserFollowVo> list = page.getResult();
        for (UserFollowVo userFollowVo : list) {

            String houseTypeName  = dictService.getNameById(userFollowVo.getHouseTypeId());
            String directionName  = dictService.getNameById(userFollowVo.getDirectionId());
            String floorName  = dictService.getNameById(userFollowVo.getFloorId());

            userFollowVo.setHouseTypeName(houseTypeName);
            userFollowVo.setFloorName(floorName);
            userFollowVo.setDirectionName(directionName);
        }
        return new PageInfo<UserFollowVo>(page,10);
    }

    //取消关注
    @Override
    public Boolean cancelFollow(Long id) {
         userFollowDao.delete(id);
        return false;
    }
}
