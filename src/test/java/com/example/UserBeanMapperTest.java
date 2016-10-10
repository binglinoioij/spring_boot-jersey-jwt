package com.example;

import com.alibaba.fastjson.JSON;
import com.example.entity.bean.MysqlUser;
import com.example.entity.bean.UserBean;
import com.example.mapper.MysqlUserMapper;
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

    @Inject
    private MysqlUserMapper mysqlUserMapper;


    @Test
    public void testFind(){
        UserBean userBean = userBeanMapper.findById(1);
        System.out.println(JsonUtils.toJsonString(userBean));
    }

    /*
    通用mapper 文档
    http://git.oschina.net/free/Mapper/blob/master/wiki/mapper3/5.Mappers.md
     */
    @Test
    public void test_findByHost(){
        MysqlUser mysqlUser = new MysqlUser();
        mysqlUser.setHost("localhost");
        System.out.println(JSON.toJSONString(mysqlUserMapper.select(mysqlUser)));
    }
}
