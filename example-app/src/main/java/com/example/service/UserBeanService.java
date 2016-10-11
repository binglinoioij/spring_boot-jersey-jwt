package com.example.service;

import com.example.entity.bean.UserBean;

import java.util.List;

/**
 * Created by binglin on 2016/10/8.
 */
public interface UserBeanService {

    List<UserBean> findAll();

    void save(UserBean userBean);
}
