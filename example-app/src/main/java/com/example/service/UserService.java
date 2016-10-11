package com.example.service;

import com.example.entity.mongo.User;
import com.example.exception.EntityNotFoundException;

import java.util.List;

/**
 * Created by binglin on 2016/10/6.
 */
public interface UserService {

    User findByUserName(String userName) throws EntityNotFoundException;

    List<User> findAll();
}
