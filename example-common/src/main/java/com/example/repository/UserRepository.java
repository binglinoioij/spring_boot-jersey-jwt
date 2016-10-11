package com.example.repository;

import com.example.entity.mongo.User;
import com.example.exception.EntityNotFoundException;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by binglin on 2016/10/6.
 */
public interface UserRepository extends PagingAndSortingRepository<User,Long> {
    User findByUserName(String userName) throws EntityNotFoundException;
}
