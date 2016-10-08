package com.example;

import com.example.entity.bean.UserBean;
import com.example.mapper.UserBeanMapper;
import com.example.util.JsonUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

/**
 * Created by binglin on 2016/10/7.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserBeanMapperTest {

    @Inject
    private UserBeanMapper userBeanMapper;

    @Test
    public void testFind(){
        UserBean userBean = userBeanMapper.findById(1);
        System.out.println(JsonUtils.toJsonString(userBean));
    }
}
