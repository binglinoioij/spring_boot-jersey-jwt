package com.example.service.impl;

import com.example.entity.bean.UserBean;
import com.example.mapper.UserBeanMapper;
import com.example.service.UserBeanService;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Context;

/**
 * Created by binglin on 2016/10/8.
 */

@Service("userBeanService")
public class UserBeanServiceImpl implements UserBeanService {

    @Inject
    private UserBeanMapper userBeanMapper;

    @Override
    public List<UserBean> findAll() {
        List<UserBean> userBeans = userBeanMapper.findAll();
        return userBeans;
    }

    @Override
    public void save(UserBean userBean) {
        userBeanMapper.save(userBean);
    }
}
