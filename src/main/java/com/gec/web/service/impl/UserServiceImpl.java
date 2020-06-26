package com.gec.web.service.impl;

import com.gec.web.mapper.TUserMapper;
import com.gec.web.pojo.TUser;
import com.gec.web.pojo.TUserExample;
import com.gec.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TUserMapper userMapper;

    @Override
    public TUser doLogin(TUser user) {
        TUserExample example = new TUserExample();
        TUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(user.getUsername());
        List<TUser> users = userMapper.selectByExample(example);

        if(users!=null && users.size()>0){//用户认证成功
            return users.get(0);
        }
        return null;
    }
}
