package com.example;

import com.alibaba.fastjson.JSON;
import com.example.entity.bean.MysqlUser;
import com.example.entity.bean.UserBean;
import com.example.mapper.MysqlUserMapper;
import com.example.mapper.UserBeanMapper;
import com.example.util.JsonUtils;
import com.github.pagehelper.PageHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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
    public void testFindByHost(){
        MysqlUser mysqlUser = new MysqlUser();
        mysqlUser.setHost("localhost");
        System.out.println(JSON.toJSONString(mysqlUserMapper.select(mysqlUser)));
    }


    /*
    分页文档
    http://git.oschina.net/free/Mybatis_PageHelper/blob/master/wikis/Important.markdown
     */
    @Test
    public void testPage(){
        PageHelper.startPage(1, 3);
        List<MysqlUser> list = mysqlUserMapper.select(new MysqlUser());
        System.out.println(JSON.toJSONString(list));

        PageHelper.startPage(1, 1);
        List<MysqlUser> list2 = mysqlUserMapper.select(new MysqlUser());
        System.out.println(JSON.toJSONString(list2));
    }
}
