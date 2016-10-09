package com.example.service.impl;

import com.example.entity.mongo.User;
import com.example.exception.EntityNotFoundException;
import com.example.repository.UserRepository;
import com.example.service.UserService;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by binglin on 2016/10/6.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Inject
    private UserRepository userRepository;

    @Override
    public User findByUserName(String userName) throws EntityNotFoundException {
        return userRepository.findByUserName(userName);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll(new PageRequest(0, 10)).getContent();
    }
}
