package com.example.mapper;

import com.example.entity.bean.UserBean;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by binglin on 2016/10/7.
 */

@Mapper
public interface UserBeanMapper {

    @Select("select * from user_bean where id = #{id}")
    UserBean findById(@Param("id") Integer id);
}
